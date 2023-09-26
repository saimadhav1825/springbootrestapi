package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name = "email_id", unique = true)
    @NotBlank(message = "Enter Your Email")
    @Email(message = "Email Should be Valid", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String emailId;

    // password should not be null or empty
    // password should have at least 8 characters
    @Column(name = "password")
    @Size(min = 8, message = "password should have at least 8 characters")
    private String password;

    @JsonIgnore
    private String role;

    @Column(name = "userName")
    private String userName="";

    private Boolean isAccountVerified = false;

    private Boolean isRegCompleted = false;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @Override
    public String toString() {
        // Exclude tokens from the toString() representation
        return "User{" +
                "id=" + id +
                ", username='" + userName + '\'' +
                // Other fields...
                '}';
    }
}
