package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.service.service_listners.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ProfileServiceImpl implements ProfileService {
    Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class);
    @Autowired
    private S3ServiceImpl s3ServiceImpl;

    @Override
    public Optional<User> updateUserProfile(String data) {

        return Optional.empty();
    }
}
