package com.project.Investo.security.config;

import com.project.Investo.security.entity.User;
import com.project.Investo.security.repository.UserRepository;
import com.project.Investo.security.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        // 🔥 GET GOOGLE USER DATA
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");
        String googleId = oauthUser.getAttribute("sub");

        // 🔥 SAVE USER IN DB IF NOT EXISTS
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {

                    User newUser = User.builder()
                            .email(email)
                            .name(name)
                            .googleId(googleId)
                            .role("USER")
                            .balance(100000) // 💰 starting money
                            .build();

                    return userRepository.save(newUser);
                });

        // 🔥 GENERATE TOKEN AFTER SAVE
        String token = jwtService.generateToken(user.getEmail());

        // 🔥 SEND TOKEN
        response.setContentType("application/json");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
    }
}