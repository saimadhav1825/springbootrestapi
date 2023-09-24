package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user/")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("createAccount")
    public ResponseEntity<?> createAccount(@Valid @RequestBody User user) {
        User userResponse = userServiceImpl.createUser(user);
        return ResponseEntity.ok(userResponse);
    }
}
