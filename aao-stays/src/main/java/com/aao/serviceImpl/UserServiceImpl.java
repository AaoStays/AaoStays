package com.aao.serviceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aao.dto.AuthResponse;
import com.aao.dto.LoginRequest;
import com.aao.dto.RegisterRequest;
import com.aao.dto.UserResponse;
import com.aao.entity.Host;
import com.aao.entity.User;
import com.aao.entity.UserType;
import com.aao.repo.HostRepo;
import com.aao.repo.UserRepo;
import com.aao.response.ApiResponse;
import com.aao.security.CustomUserDetails;
import com.aao.security.JwtService;
import com.aao.service.EmailService;
import com.aao.service.VerificationTokenService;
import com.aao.serviceInterface.IUserService;
import com.aao.utils.UserMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final HostRepo hostRepo;
    
    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private EmailService emailService;


    @Override
    @Transactional
    public ApiResponse<AuthResponse> register(RegisterRequest request) {

        if (request == null) 
            return new ApiResponse<>(400, "Invalid Request", null);
        
   
        if (request.getFullName() == null || request.getFullName().length() < 3) {
            return new ApiResponse<>(400, "Full name must be at least 3 characters", null);
        }

       
        if (request.getPhoneNumber() == null || !request.getPhoneNumber().matches("\\d{10}")) {
            return new ApiResponse<>(400, "Phone number must be exactly 10 digits", null);
        }

      
        String password = request.getPassword();
        if (password.length() < 8 || password.length() > 20) {
            return new ApiResponse<>(400, "Password must be 8–20 characters", null);
        }

        
        if (!password.matches(".*[A-Z].*") ||
            !password.matches(".*[a-z].*") ||
            !password.matches(".*\\d.*") ||
            !password.matches(".*[!@#$%^&*()].*")) {
            return new ApiResponse<>(400,
                "Password must include uppercase, lowercase, number and special character", null);
        }

 
        if (request.getDateOfBirth() != null &&
            request.getDateOfBirth().isAfter(LocalDate.now())) {
            return new ApiResponse<>(400, "Date of birth cannot be in the future", null);
        }


        if (userRepo.existsByEmail(request.getEmail())) {
            return new ApiResponse<>(400, "Email already exists", null);
        }

        if (userRepo.existsByPhoneNumber(request.getPhoneNumber())) {
            return new ApiResponse<>(400, "Phone number already exists", null);
        }

       
        
        
        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .userType(UserType.GUEST)   
                .profilePictureUrl(request.getProfilePictureUrl())
                .dateOfBirth(request.getDateOfBirth())
                .address(request.getAddress())
                .isActive(true)
                .isEmailVerified(false)
                .isPhoneVerified(false)
                .build();


        User saved = userRepo.save(user);
        String token = verificationTokenService.generateEmailVerificationToken(saved.getEmail());
        String link = "http://localhost:8080/api/auth/verify-email?token=" + token;

        emailService.sendEmailVerificationLink(saved.getEmail(), link);


       
        AuthResponse auth = AuthResponse.builder()
                .accessToken(jwtService.generateAccessToken(saved))
                .refreshToken(jwtService.generateRefreshToken(saved))
                .tokenType("Bearer")                          
                .userId(saved.getId())
                .email(saved.getEmail())
                .role(saved.getUserType().name())
                .build();

        return new ApiResponse<>(200, "Registration Successful", auth);
    }

    @Override
    public ApiResponse<AuthResponse> login(LoginRequest request) {

        if (request == null)
            return new ApiResponse<>(400, "Invalid Request", null);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
            return new ApiResponse<>(401, "Invalid Email or Password", null);
        }

        
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getIsActive())
            return new ApiResponse<>(403, "Account Disabled", null);

        if (!user.getIsEmailVerified()) {
            return new ApiResponse<>(403, "Please verify your email before logging in", null);
        }

        user.setLastLogin(java.time.LocalDateTime.now());
        userRepo.save(user);

        emailService.sendLoginNotification(user.getEmail(), user.getFullName());

        AuthResponse auth = AuthResponse.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .tokenType("Bearer")
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getUserType().name())
                .build();

        return new ApiResponse<>(200, "Login Successful", auth);
    }



    
    @Override
    public ApiResponse<UserResponse> deleteUserById(Long id){
    	
    	User user = userRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("User not found"));
    	
    	userRepo.delete(user);
    	
    	return new ApiResponse<UserResponse>(200, "User deleted Successfully", null);
    	
    }
    
    @Override
      public ApiResponse<UserResponse> getUserById(Long id){
    	   
    	User user = userRepo.findById(id).orElseThrow(()-> new IllegalArgumentException(" user not found"));    
    	
    	UserResponse response= UserMapper.mapUserToUserResponse(user);
    	
    	return new ApiResponse<UserResponse>(200, "User fetched successfully ", response);
    
    }
    
    @Override
    public ApiResponse<List<UserResponse>> getAllUsers() {

        List<User> users = userRepo.findAll();

        List<UserResponse> response = users.stream()
                .map(UserMapper::mapUserToUserResponse)
                .toList();

        return new ApiResponse<>(200, "Users fetched successfully", response);
    }

	
    @Transactional
    public ApiResponse<String> updateUserRole(Long userId, String newRole) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

       
        UserType userType = UserType.valueOf(newRole.toUpperCase());

        // Update user role
        user.setUserType(userType);
        userRepo.save(user);

        // ⭐ AUTO CREATE HOST IF ROLE = HOST
        if (userType == UserType.HOST && !hostRepo.existsByUser_Id(userId)) {

            Host host = new Host();
            host.setUser(user);
            host.setCompanyName(user.getFullName() + " Properties");

            host.setBio(null);
            host.setGstNumber(null);
            host.setProfilePicture(null);
            host.setLanguagesSpoken(null);

            host.setHostSince(LocalDate.now());
            host.setVerificationStatus("PENDING");
            host.setIsSuperhost(false);
            host.setIsVerified(false);
            host.setIdVerified(false);
            host.setStatus("ACTIVE");

         
            host.setTotalProperties(0);
            host.setActiveProperties(0);
            host.setTotalBookings(0);
            host.setTotalEarnings(BigDecimal.ZERO);
            host.setEarningsPerMonth(BigDecimal.ZERO);
            host.setAverageRating(BigDecimal.ZERO);
            host.setResponseRate(BigDecimal.ZERO);
            host.setResponseTime(0);

            hostRepo.save(host);  
        }

        return new ApiResponse<>(200, "Role updated & host created if required", null);
    }

}
