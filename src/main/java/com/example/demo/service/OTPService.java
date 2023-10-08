package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class OTPService {

    public String generate6DigitOTP() {
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000); // Generates a random 6-digit OTP
        return String.valueOf(otpValue);
    }
}
