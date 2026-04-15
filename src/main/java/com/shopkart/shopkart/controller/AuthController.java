package com.shopkart.shopkart.controller;

import com.shopkart.shopkart.dto.request.LoginRequest;
import com.shopkart.shopkart.dto.request.RegisterRequest;
import com.shopkart.shopkart.dto.response.AuthResponse;
import com.shopkart.shopkart.service.AuthService;
import com.shopkart.shopkart.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return new ResponseEntity<>(ApiResponse.success("User registered successfully", response), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return new ResponseEntity<>(ApiResponse.success("Login successful", response), HttpStatus.OK);
    }
}