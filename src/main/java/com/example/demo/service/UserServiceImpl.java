package com.example.demo.service;

import com.example.demo.enums.TokenType;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.model.BaseResponse;
import com.example.demo.model.Token;
import com.example.demo.model.User;
import com.example.demo.model.request.AuthenticationRequest;
import com.example.demo.model.request.DisplayNameRequest;
import com.example.demo.model.request.RegistrationRequest;
import com.example.demo.model.response.LoginResponse;
import com.example.demo.model.response.UserDetailsResponse;
import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public BaseResponse createUser(RegistrationRequest registrationRequest) {
        if (userRepository.findByEmailId(registrationRequest.getEmailId()).isPresent()) {
            throw new UserAlreadyExistsException("Email Already Exist");
        }
        User user = new User();
        user.setEmailId(registrationRequest.getEmailId());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setRole("User");
        user.setUserName(null);
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, jwtToken);
        UserDetailsResponse userDetails = new UserDetailsResponse(registrationRequest.getEmailId(), "", false, false);
        return new BaseResponse(true, "Account Created Successfully", userDetails);
    }

    @Override
    public BaseResponse addUserName(DisplayNameRequest displayNameRequest) {
        BaseResponse baseResponse;
        if (userRepository.findByUserName(displayNameRequest.getName()).isPresent()) {
            throw new UserAlreadyExistsException("Name Already Took By SomeOne");
        } else {
            Optional<User> user = userRepository.findByEmailId(displayNameRequest.getEmailId());
            if (user.isPresent()) {
                UserDetailsResponse userDetails = new UserDetailsResponse(user.get().getEmailId(),
                        displayNameRequest.getName(), user.get().getIsAccountVerified(), user.get().getIsRegCompleted());
                user.get().setUserName(displayNameRequest.getName());
                userRepository.save(user.get());
                baseResponse = new BaseResponse(true, "Name Added Successfully", userDetails);
            } else
                throw new ResourceNotFoundException("Account Not Found With This Email " + displayNameRequest.getEmailId());
        }
        return baseResponse;
    }

    public BaseResponse authenticateAndGetToken(AuthenticationRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmailId(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                var user = userRepository.findByEmailId(authRequest.getEmailId())
                        .orElseThrow();
                if (user.getUserName() == null) {
                    throw new CustomException("Please Complete Registration");
                }
                var jwtToken = jwtService.generateToken(user);
                var refreshToken = jwtService.generateRefreshToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, jwtToken);
                return new LoginResponse(true, "Login Successfully", jwtToken, refreshToken);
            } else throw new BadCredentialsException("Invalid credentials");
        } catch (BadCredentialsException ex) {
            throw ex;
        }
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmailId(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
            }
        }
    }

    //Save Token
    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

}
