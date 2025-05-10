package com.example.SalesProject.controller;

import com.example.SalesProject.dto.LoginRequest;
import com.example.SalesProject.dto.SignupRequest;
import com.example.SalesProject.service.AuthService;
import com.example.SalesProject.util.TokenBlacklist;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenBlacklist tokenBlacklist;

    // POST request for login
    @PostMapping("/login")
    public String logIn(@RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (token != null) {
            return "Login successful! Token: " + token;
        } else {
            return "Invalid credentials!";
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenBlacklist.blacklist(token);
        }
        return ResponseEntity.ok("Logged out successfully.");
    }

}
