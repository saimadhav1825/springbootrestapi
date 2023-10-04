package com.example.demo.service.service_listners;

import com.example.demo.model.User;

import java.util.Optional;

public interface ProfileService {

    Optional<User> updateUserProfile(String data);
}
