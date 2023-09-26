package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
    private Boolean status;
    private String message;

    private Object data;

    public BaseResponse(Boolean status, String message) {
        this.status = status;
        this.message = message;
    }
}