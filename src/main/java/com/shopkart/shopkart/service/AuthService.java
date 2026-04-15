package com.shopkart.shopkart.service;

import com.shopkart.shopkart.dto.request.LoginRequest;
import com.shopkart.shopkart.dto.request.RegisterRequest;
import com.shopkart.shopkart.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);
    AuthResponse register(RegisterRequest registerRequest);
}