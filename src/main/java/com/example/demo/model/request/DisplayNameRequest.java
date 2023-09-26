package com.example.demo.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class DisplayNameRequest {
    @NotBlank(message = "Enter Your Email")
    @Email(message = "Email Should be Valid", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String emailId;

    @NotBlank(message = "Enter Your Name")
    @Size(min = 6, message = "Name should have at least 8 characters")
    private String name;
}
