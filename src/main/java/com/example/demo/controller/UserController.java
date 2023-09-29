package com.example.demo.controller;

import com.example.demo.model.BaseResponse;
import com.example.demo.model.User;
import com.example.demo.model.request.AuthenticationRequest;
import com.example.demo.model.request.DisplayNameRequest;
import com.example.demo.model.request.RegistrationRequest;
import com.example.demo.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/registration")
    public ResponseEntity<?> createAccount(@Valid @RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(userServiceImpl.createUser(registrationRequest));
    }

    @PostMapping("/addDisplayName")
    public ResponseEntity<?> displayName(@Valid @RequestBody DisplayNameRequest displayNameRequest) {
        return ResponseEntity.ok(userServiceImpl.addUserName(displayNameRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthenticationRequest authRequest) {
        return ResponseEntity.ok(userServiceImpl.authenticateAndGetToken(authRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        userServiceImpl.refreshToken(request, response);
    }

    @GetMapping("/home")
    public ResponseEntity<?> home() {
        return ResponseEntity.ok("HomeData");
    }
}
