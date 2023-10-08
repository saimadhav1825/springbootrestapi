package com.example.demo.service.service_listners;

import com.example.demo.model.BaseResponse;
import com.example.demo.model.request.DisplayNameRequest;
import com.example.demo.model.request.RegistrationRequest;
import com.example.demo.model.request.ResetPasswordRequest;


public interface UserService {

    BaseResponse createUser(RegistrationRequest registrationRequest);

    BaseResponse addUserName(DisplayNameRequest displayNameRequest);

    BaseResponse phoneNumberOtpSend(String phoneNumber);

    BaseResponse forgotPassword(String email);
    BaseResponse resetPassword(ResetPasswordRequest resetPasswordRequest);



}
