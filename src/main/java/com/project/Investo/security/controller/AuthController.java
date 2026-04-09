package com.project.Investo.security.controller;


import com.project.Investo.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/google-login")
    public String googleLogin(@RequestParam String email) {

        return jwtService.generateToken(email);
    }
}
