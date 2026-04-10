package com.project.Investo.security.controller;


import com.project.Investo.security.dto.AuthResponse;
import com.project.Investo.security.dto.LoginRequest;
import com.project.Investo.security.dto.RegisterRequest;
import com.project.Investo.security.entity.User;
import com.project.Investo.security.repository.UserRepository;
import com.project.Investo.security.service.AuthService;
import com.project.Investo.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthService authService;

    @PostMapping("/google-login")
    public String googleLogin(@RequestParam String email) {

        return jwtService.generateToken(email);
    }
    @PostMapping("/add-mobile")
    public String addMobile(@RequestParam String mobile) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        user.setMobileNumber(mobile);
        userRepository.save(user);

        return "Mobile added successfully ✅";
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
