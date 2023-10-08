package com.example.demo.model.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
    @NotBlank(message = "Enter Your Email")
    @Email(message = "Email Should be Valid", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String emailId;

    @NotBlank(message = "tempPassword is Required")
    @Size(min = 8, message = "TempPassword should have at least 8 characters")
    private String tempPassword;
    @NotBlank(message = "Password is Required")
    @Size(min = 8, message = "Password should have at least 8 characters")
    private String password;
}
