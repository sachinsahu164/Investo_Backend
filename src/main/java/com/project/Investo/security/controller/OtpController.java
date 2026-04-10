package com.project.Investo.security.controller;

import com.project.Investo.security.entity.User;
import com.project.Investo.security.repository.UserRepository;
import com.project.Investo.security.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class OtpController {

    private final OtpService otpService;
    private final UserRepository userRepository;

    // 🔥 Send OTP (JWT se user identify)
    @PostMapping("/send-otp")
    public String sendOtp() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        return otpService.sendOtp(user.getMobileNumber());
    }

    // 🔥 Verify OTP
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String otp) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        boolean isValid =
                otpService.verifyOtp(user.getMobileNumber(), otp);

        if (isValid) {
            return "Mobile verified successfully ✅";
        }

        return "Invalid OTP ❌";
    }
}