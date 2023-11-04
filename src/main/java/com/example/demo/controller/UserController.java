package com.example.demo.controller;

import com.example.demo.model.request.*;
import com.example.demo.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.example.demo.ApiConstants.*;

//http://localhost:8080/
@RestController
@RequestMapping("api/v1/user/")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping(CREATE_ACCOUNT)
    public ResponseEntity<?> createAccount(@Valid @RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(userServiceImpl.createUser(registrationRequest));
    }

    @PostMapping(ADD_DISPLAY_NAME)
    public ResponseEntity<?> displayName(@Valid @RequestBody DisplayNameRequest displayNameRequest) {
        return ResponseEntity.ok(userServiceImpl.addUserName(displayNameRequest));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequest authRequest) {
        return ResponseEntity.ok(userServiceImpl.authenticateAndGetToken(authRequest));
    }

    @PostMapping(REFRESH_TOKEN)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        userServiceImpl.refreshToken(request, response);
    }

    @PostMapping(PHONE_NUMBER_OTP)
    public ResponseEntity<?> phoneNumberOtpSend(@Valid @RequestBody PhoneRequest phoneRequest) {
        return ResponseEntity.ok(userServiceImpl.phoneNumberOtpSend(phoneRequest.getPhoneNumber()));
    }

    @PostMapping(FORGOT_PASSWORD)
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        return ResponseEntity.ok(userServiceImpl.forgotPassword(forgotPasswordRequest.getEmailId()));
    }

    @PostMapping(VERIFY_OTP)
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        return ResponseEntity.ok(userServiceImpl.forgotPassword(forgotPasswordRequest.getEmailId()));
    }

    @PutMapping(RESET_PASSWORD)
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        return ResponseEntity.ok(userServiceImpl.resetPassword(resetPasswordRequest));
    }

}
