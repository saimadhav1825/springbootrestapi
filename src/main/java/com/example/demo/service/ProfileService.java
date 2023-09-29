package com.example.demo.service;

import com.example.demo.model.User;

import java.util.Optional;

public interface ProfileService {

    Optional<User> updateUserProfile(String data);
}
