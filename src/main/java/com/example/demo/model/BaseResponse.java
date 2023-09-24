package com.example.demo.model;

import lombok.*;

@Data
public class BaseResponse {
    Boolean success;
    String message;

    public BaseResponse(Boolean success, String errorMessage) {
        this.success = success;
        this.message = errorMessage;
    }
}
