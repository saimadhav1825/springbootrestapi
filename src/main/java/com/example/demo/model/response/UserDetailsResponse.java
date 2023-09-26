package com.example.demo.model.response;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsResponse {
    String email;
    String name;
    Boolean isVerified;
    Boolean isRegistrationCompleted;
}
