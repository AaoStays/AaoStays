package com.aao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aao.dto.LoginRequest;
import com.aao.dto.RegisterRequest;
import com.aao.response.ApiResponse;
import com.aao.dto.AuthResponse;
import com.aao.serviceInterface.IUserService;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IUserService userService;

   
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody RegisterRequest request) {
        ApiResponse<AuthResponse> response = userService.register(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

 
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        ApiResponse<AuthResponse> response = userService.login(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
