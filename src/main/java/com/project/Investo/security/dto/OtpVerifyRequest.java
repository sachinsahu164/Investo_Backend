package com.project.Investo.security.dto;


import lombok.Data;

@Data
public class OtpVerifyRequest {

    private String mobileNumber;

    private String otp;
}