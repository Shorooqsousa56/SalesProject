package com.example.SalesProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.SalesProject.dto.SignupRequest;
import com.example.SalesProject.entity.Users;
import com.example.SalesProject.repository.UserRepository;
import com.example.SalesProject.util.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(String email, String password) {
        // Retrieve the user by email
        Users user = userRepository.findByEmail(email).orElse(null);
        System.out.println("User exists: " + (user != null));
        System.out.println("Raw password: " + password);
        // Check if user exists and the password matches
        if (user != null) {
            System.out.println("Stored (hashed) password: " + user.getPassword());
            boolean matches = passwordEncoder.matches(password, user.getPassword());
            System.out.println("Password matches: " + matches);

            if (matches) {
                return jwtUtil.generateToken(email, user.getRole()); // This generates a JWT token for the user
            }
        }

        // If authentication fails, return null or throw exception
        return null; // Or you can throw an exception like "Invalid credentials"
    }

    public String signup(SignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        Users newUser = new Users();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setName(request.getName());
        newUser.setRole(request.getRole());
        newUser.setPhone(request.getPhone());
        newUser.setAddress(request.getAddress());

        userRepository.save(newUser);
        return "Signup successful";
    }

}
