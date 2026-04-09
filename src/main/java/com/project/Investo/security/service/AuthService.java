package com.project.Investo.security.service;


import com.project.Investo.security.dto.AuthResponse;
import com.project.Investo.security.dto.GoogleAuthRequest;
import com.project.Investo.security.entity.User;
import com.project.Investo.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

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
}