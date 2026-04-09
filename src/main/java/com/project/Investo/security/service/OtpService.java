package com.project.Investo.security.service;


import com.project.Investo.security.entity.OtpVerification;
import com.project.Investo.security.repository.OtpRepository;
import com.project.Investo.security.util.OtpGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpRepository otpRepository;

    public String sendOtp(String mobile) {

        String otp = OtpGenerator.generate();

        OtpVerification entity = OtpVerification.builder()
                .mobileNumber(mobile)
                .otp(otp)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .build();

        otpRepository.save(entity);

        return otp;
    }

    public boolean verifyOtp(String mobile, String otp) {

        OtpVerification entity =
                otpRepository.findByMobileNumber(mobile).orElseThrow();

        return entity.getOtp().equals(otp);
    }
}