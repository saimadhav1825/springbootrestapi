package com.example.demo.service.service_listners;

import com.example.demo.model.BaseResponse;
import com.example.demo.model.request.DisplayNameRequest;
import com.example.demo.model.request.RegistrationRequest;


public interface UserService {

    BaseResponse createUser(RegistrationRequest registrationRequest);

    BaseResponse addUserName(DisplayNameRequest displayNameRequest);



}