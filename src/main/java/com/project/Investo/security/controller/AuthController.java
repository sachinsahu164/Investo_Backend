package com.project.Investo.security.controller;


import com.project.Investo.security.entity.User;
import com.project.Investo.security.repository.UserRepository;
import com.project.Investo.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final UserRepository userRepository;

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
}
