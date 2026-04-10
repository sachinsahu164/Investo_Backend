package com.project.Investo.security.service;


import com.project.Investo.security.entity.OtpVerification;
import com.project.Investo.security.entity.User;
import com.project.Investo.security.repository.OtpRepository;
import com.project.Investo.security.repository.UserRepository;
import com.project.Investo.security.util.OtpGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpRepository otpRepository;
    private final UserRepository userRepository;

    // 🔥 SEND OTP
    public String sendOtp(String mobile) {

        String otp = OtpGenerator.generate();

        OtpVerification entity = OtpVerification.builder()
                .mobileNumber(mobile)
                .otp(otp)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .build();

        otpRepository.save(entity);

        System.out.println("📱 OTP: " + otp); // 🔥 console OTP

        return otp;
    }

    // 🔥 VERIFY OTP
    public boolean verifyOtp(String mobile, String otp) {

        OtpVerification entity =
                otpRepository.findByMobileNumber(mobile).orElseThrow();

        if (!entity.getOtp().equals(otp)) {
            return false;
        }

        // 🔥 User update (important)
        User user = userRepository.findByMobileNumber(mobile)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setMobileVerified(true);
        userRepository.save(user);

        return true;
    }
}