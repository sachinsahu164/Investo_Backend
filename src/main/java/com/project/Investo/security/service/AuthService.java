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

    public AuthResponse googleLogin(GoogleAuthRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseGet(() -> {

                    User newUser = User.builder()
                            .email(request.getEmail())
                            .name(request.getName())
                            .googleId(request.getGoogleId())
                            .mobileVerified(false)
                            .role("USER")
                            .build();

                    return userRepository.save(newUser);
                });

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(
                token,
                "Login successful"
        );
    }

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
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token, "User registered successfully");
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token, "Login successful");
    }
}