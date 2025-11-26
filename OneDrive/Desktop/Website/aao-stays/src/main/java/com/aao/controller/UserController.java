package com.aao.controller;

import com.aao.dto.*;
import com.aao.response.ApiResponse;
import com.aao.serviceInterface.IUserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    // ================================================================
    // REGISTER
    // ================================================================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        ApiResponse<?> response = userService.register(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // ================================================================
    // LOGIN
    // ================================================================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        ApiResponse<?> response = userService.login(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // ================================================================
    // UPDATE EMAIL
    // ================================================================
    @PutMapping("/{id}/email")
    public ResponseEntity<?> updateEmail(
            @PathVariable Long id,
            @RequestBody UpdateEmailRequest request) {

        ApiResponse<?> response = userService.updateEmail(id, request.getNewEmail());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // ================================================================
    // UPDATE PASSWORD
    // ================================================================
    @PutMapping("/{id}/password")
    public ResponseEntity<?> updatePassword(
            @PathVariable Long id,
            @RequestBody UpdatePasswordRequest request) {

        ApiResponse<?> response = userService.updatePassword(
                id,
                request.getOldPassword(),
                request.getNewPassword()
        );

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // ================================================================
    // UPDATE USERNAME
    // ================================================================
    @PutMapping("/{id}/username")
    public ResponseEntity<?> updateUsername(
            @PathVariable Long id,
            @RequestBody UpdateUsernameRequest request) {

        ApiResponse<?> response = userService.updateUsername(id, request.getNewUsername());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
