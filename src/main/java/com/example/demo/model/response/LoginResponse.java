package com.example.demo.model.response;

import com.example.demo.model.BaseResponse;
import lombok.*;

import javax.management.ConstructorParameters;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse  extends BaseResponse{
    private String token;

    // Constructor with success, message, and token
    public LoginResponse(Boolean success, String message, String token) {
        super(success, message);
        this.token = token;
    }
}
