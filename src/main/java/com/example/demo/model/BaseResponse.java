package com.example.demo.model;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseResponse {
    Boolean success;
    String message;
}
