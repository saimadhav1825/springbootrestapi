package com.example.demo.service;

import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(User user) {
        if (userRepository.findByEmailId(user.getEmailId()).isPresent()) {
            throw new UserAlreadyExistsException("Email Already Exist");
        }
        return userRepository.save(user);
    }
}
