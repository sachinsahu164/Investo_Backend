package com.project.Investo.security.service;

import com.project.Investo.security.dto.AuthResponse;
import com.project.Investo.security.dto.GoogleAuthRequest;
import com.project.Investo.security.dto.LoginRequest;
import com.project.Investo.security.dto.RegisterRequest;
import com.project.Investo.security.entity.User;
import com.project.Investo.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // ✅ GOOGLE LOGIN (FINAL FIXED)
    public AuthResponse googleLogin(GoogleAuthRequest request) {

        if (request.getEmail() == null) {
            throw new RuntimeException("Google email not found");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .map(existingUser -> {

                    // ✅ agar existing user hai but googleId nahi hai → update karo
                    if (existingUser.getGoogleId() == null) {
                        existingUser.setGoogleId(request.getGoogleId());
                        return userRepository.save(existingUser);
                    }

                    return existingUser;
                })
                .orElseGet(() -> {

                    // ✅ NEW USER CREATE
                    User newUser = User.builder()
                            .email(request.getEmail())
                            .name(request.getName())
                            .googleId(request.getGoogleId())
                            .mobileVerified(false)
                            .role("USER")
                            .balance(100000.0) // 👈 default balance
                            .build();

                    return userRepository.save(newUser);
                });

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token, "Google login successful");
    }

    // ✅ REGISTER (IMPROVED)
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .mobileVerified(false)
                .role("USER")
                .balance(100000.0)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token, "User registered successfully");
    }

    // ✅ LOGIN (SAFE VERSION)
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ⚠️ IMPORTANT FIX: Google user ke paas password null hota hai
        if (user.getPassword() == null) {
            throw new RuntimeException("Please login with Google");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token, "Login successful");
    }
}