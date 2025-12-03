package com.aao.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aao.entity.User;
import com.aao.repo.UserRepo;
import com.aao.response.ApiResponse;
import com.aao.service.VerificationTokenService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EmailVerificationController {
	
	private final VerificationTokenService verificationTokenService;
    private final UserRepo userRepo;

    @GetMapping("/api/auth/verify-email")
    public ApiResponse<String> verifyEmail(@RequestParam("token") String token) {

        var emailOpt = verificationTokenService.validateAndExtractEmail(token);

        if (emailOpt.isEmpty()) {
            return new ApiResponse<>(400, "Invalid or expired verification token", null);
        }

        String email = emailOpt.get();

        User user = userRepo.findByEmail(email).orElse(null);

        if (user == null) {
            return new ApiResponse<>(404, "User does not exist", null);
        }

        user.setIsEmailVerified(true);
        userRepo.save(user);

        return new ApiResponse<>(200, "Email verified successfully", "VERIFIED");
    }
}
