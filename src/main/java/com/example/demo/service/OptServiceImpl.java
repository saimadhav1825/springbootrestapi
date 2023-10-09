package com.example.demo.service;

import com.example.demo.model.BaseResponse;
import com.example.demo.model.Otp;
import com.example.demo.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class OptServiceImpl implements OTPService {

    @Autowired
    private OtpRepository otpRepository;

    @Override
    public BaseResponse saveOtp(String id) {
        UUID uuid = UUID.fromString(id);
        // Find the OTP entity by UUID
        Otp otp = otpRepository.findByUserId(uuid).orElse(null);

        if (otp != null) {
            // Update the OTP value
            String otpNumber = generate6DigitOTP();
            otp.setCode(otpNumber);
            otpRepository.save(otp); // Save the updated OTP entity
        }else {

        }
        return new BaseResponse(true, "Password Send To Your Mobile Number");
    }

    @Override
    public String generate6DigitOTP() {
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000); // Generates a random 6-digit OTP
        return String.valueOf(otpValue);
    }
}
