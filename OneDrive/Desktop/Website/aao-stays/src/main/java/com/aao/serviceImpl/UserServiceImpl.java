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

    // ========================================================================
    //  REGISTER
    // ========================================================================
    @Override
    @Transactional
    public ApiResponse<AuthResponse> register(RegisterRequest request) {

        if (request == null) 
            return new ApiResponse<>(400, "Invalid Request", null);

        if (userRepo.existsByEmail(request.getEmail()))
            return new ApiResponse<>(400, "Email Already Exists", null);

        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
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

    // ========================================================================
    //  LOGIN
    // ========================================================================
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

    // ========================================================================
    //  UPDATE EMAIL
    // ========================================================================
    @Transactional
    public ApiResponse<String> updateEmail(Long userId, String newEmail) {

        if (newEmail == null || newEmail.isBlank()) {
            return new ApiResponse<>(400, "Email cannot be empty", null);
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (newEmail.equals(user.getEmail())) {
            return new ApiResponse<>(400, "New email cannot be same as current email", null);
        }

        if (userRepo.existsByEmail(newEmail)) {
            return new ApiResponse<>(400, "Email already exists", null);
        }

        user.setEmail(newEmail);
        user.setIsEmailVerified(false); // Optional: force verification
        userRepo.save(user);

        return new ApiResponse<>(200, "Email updated successfully", "UPDATED");
    }

    // ========================================================================
    //  UPDATE PASSWORD
    // ========================================================================
    @Transactional
    public ApiResponse<String> updatePassword(Long userId, String oldPassword, String newPassword) {

        if (oldPassword == null || newPassword == null) {
            return new ApiResponse<>(400, "Password fields cannot be empty", null);
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            return new ApiResponse<>(401, "Old password is incorrect", null);
        }

        if (passwordEncoder.matches(newPassword, user.getPasswordHash())) {
            return new ApiResponse<>(400, "New password cannot be same as old password", null);
        }

        if (newPassword.length() < 8) {
            return new ApiResponse<>(400, "Password must be at least 8 characters long", null);
        }

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepo.save(user);

        return new ApiResponse<>(200, "Password updated successfully", "UPDATED");
    }

    // ========================================================================
    //  UPDATE USERNAME
    // ========================================================================
    @Transactional
    public ApiResponse<String> updateUsername(Long userId, String newUsername) {

        if (newUsername == null || newUsername.isBlank()) {
            return new ApiResponse<>(400, "Username cannot be empty", null);
        }

        if (!newUsername.matches("^[a-zA-Z0-9_]{3,30}$")) {
            return new ApiResponse<>(400, "Invalid username format", null);
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (newUsername.equals(user.getFullName())) {
            return new ApiResponse<>(400, "New username cannot be same as current", null);
        }

        if (userRepo.existsByEmail(newUsername)) {
            return new ApiResponse<>(400, "Username already taken", null);
        }

        user.setFullName(newUsername);
        userRepo.save(user);

        return new ApiResponse<>(200, "Username updated successfully", "UPDATED");
    }

	public Object updateEmail(Long userId, Object newEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	public ApiResponse updatePassword(Long userId, Object oldPassword, Object newPassword) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object updateUsername(Long userId, Object newUsername) {
		// TODO Auto-generated method stub
		return null;
	}
}
