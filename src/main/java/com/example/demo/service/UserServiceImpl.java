package com.example.demo.service;

import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.model.BaseResponse;
import com.example.demo.model.User;
import com.example.demo.model.request.AuthenticationRequest;
import com.example.demo.model.response.LoginResponse;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(User user) {
        if (userRepository.findByEmailId(user.getEmailId()).isPresent()) {
            throw new UserAlreadyExistsException("Email Already Exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("User");
        return userRepository.save(user);
    }

    public BaseResponse authenticateAndGetToken(AuthenticationRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmailId(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                return new LoginResponse(true, "Login Successfully", jwtService.generateToken(authRequest.getEmailId()));
            } else {
                throw new BadCredentialsException("Invalid credentials");
            }
        } catch (BadCredentialsException ex) {
            throw ex;
        }
    }
}
