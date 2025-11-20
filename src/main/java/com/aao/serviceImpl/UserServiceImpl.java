package com.aao.serviceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aao.dto.AuthResponse;
import com.aao.dto.LoginRequest;
import com.aao.dto.RegisterRequest;
import com.aao.entity.User;
import com.aao.repo.UserRepo;
import com.aao.response.ApiResponse;
import com.aao.security.JwtService;
import com.aao.serviceInterface.IUserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public ApiResponse<AuthResponse> register(RegisterRequest request) {

        if (request == null) 
            return new ApiResponse<>(400, "Invalid Request", null);

        if (userRepo.existsByEmail(request.getEmail()))
            return new ApiResponse<>(400, "Email Already Exists", null);

        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))   // FIXED
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .userType(request.getUserType())
                .profilePictureUrl(request.getProfilePictureUrl())
                .dateOfBirth(request.getDateOfBirth())
                .address(request.getAddress())
                .isActive(true)
                .isEmailVerified(false)
                .isPhoneVerified(false)
                .build();

        User saved = userRepo.save(user);

        // Generate tokens for registration
        AuthResponse auth = AuthResponse.builder()
                .accessToken(jwtService.generateAccessToken(saved))
                .refreshToken(jwtService.generateRefreshToken(saved))
                .tokenType("Bearer")                          // Make sure field exists
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
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));

        if (!user.getIsActive())
            return new ApiResponse<>(403, "Account Disabled", null);

        user.setLastLogin(java.time.LocalDateTime.now());
        userRepo.save(user);

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
}
