package com.project.Investo.security.controller;


import com.project.Investo.security.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class OtpController {

    private final OtpService otpService;

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String mobile) {

        return otpService.sendOtp(mobile);
    }

    @PostMapping("/verify-otp")
    public boolean verifyOtp(
            @RequestParam String mobile,
            @RequestParam String otp
    ) {

        return otpService.verifyOtp(mobile, otp);
    }
}
