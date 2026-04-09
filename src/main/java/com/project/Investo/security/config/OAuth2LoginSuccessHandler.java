package com.project.Investo.security.config;


import com.project.Investo.security.entity.User;
import com.project.Investo.security.repository.UserRepository;
import com.project.Investo.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        // 1️⃣ Google se data lena
        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");
        String googleId = oauthUser.getAttribute("sub");

        // 2️⃣ DB me user check / create
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .email(email)
                            .name(name)
                            .googleId(googleId)
                            .mobileVerified(false)
                            .role("USER")
                            .build();

                    return userRepository.save(newUser);
                });

        // 3️⃣ JWT generate
        String token = jwtService.generateToken(user.getEmail());

        // 4️⃣ Console me print (tumhari requirement)
        System.out.println("🔥 JWT TOKEN: " + token);
        System.out.println("👤 USER: " + user.getEmail());

        // 5️⃣ Response me bhi bhejna
        response.setContentType("application/json");

        response.getWriter().write(
                "{ \"token\": \"" + token + "\", \"email\": \"" + email + "\" }"
        );
    }
}