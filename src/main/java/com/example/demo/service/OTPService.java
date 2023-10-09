package com.example.demo.service;

import com.example.demo.model.BaseResponse;
import org.springframework.stereotype.Service;

import java.util.Random;



public interface OTPService {

    BaseResponse saveOtp(String id);
    String generate6DigitOTP();
}
