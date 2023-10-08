package com.example.demo.service;

import com.example.demo.model.BaseResponse;
import com.example.demo.repository.TokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Handle invalid or missing Authorization header
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid or missing Authorization header");
            return;
        }
        jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
            // Send a successful logout response
            BaseResponse successResponse = new BaseResponse(true, "Logout successful");
            sendJsonResponse(response, HttpServletResponse.SC_OK, successResponse);
        } else {
            // Token not found, possibly expired or already revoked
            sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, "Token not found or already revoked");
        }
    }

    private void sendJsonResponse(HttpServletResponse response, int status, Object responseObject) {
        response.setStatus(status);
        response.setHeader("Content-Type", "application/json");
        try {
            PrintWriter writer = response.getWriter();
            ObjectMapper objectMapper = new ObjectMapper();
            writer.println(objectMapper.writeValueAsString(responseObject));
        } catch (IOException e) {
            // Handle any IOException that may occur while writing the response
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String errorMessage) {
        BaseResponse errorResponse = new BaseResponse(false, errorMessage);
        sendJsonResponse(response, status, errorResponse);
    }
}
