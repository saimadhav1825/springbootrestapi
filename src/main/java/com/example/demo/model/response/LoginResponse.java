package com.example.demo.model.response;

import com.example.demo.model.BaseResponse;
import lombok.*;

import javax.management.ConstructorParameters;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse extends BaseResponse {
    private String accessToken;
    private String refreshToken;
    // Constructor with success, message, accessToken and refreshToken
    public LoginResponse(Boolean success, String message, String accessToken, String refreshToken) {
        super(success, message);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
