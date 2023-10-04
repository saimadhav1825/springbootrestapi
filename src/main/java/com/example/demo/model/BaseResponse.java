package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
    private Boolean status;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public BaseResponse(Boolean status, String message) {
        this.status = status;
        this.message = message;
    }
}