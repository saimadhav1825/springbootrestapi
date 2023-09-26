package com.example.demo.controller;

import com.example.demo.exception.*;
import com.example.demo.model.BaseResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        FieldError firstError = ex.getBindingResult().getFieldError();
        assert firstError != null;
        String errorMessage = firstError.getDefaultMessage();
        return new ResponseEntity<>(new BaseResponse(false, errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<BaseResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return new ResponseEntity<>(new BaseResponse(false, ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BaseResponse> handleUserNotFoundException(BadCredentialsException ex) {
        return new ResponseEntity<>(new BaseResponse(false, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<BaseResponse> handleMailException(MailException ex) {
        return new ResponseEntity<>(new BaseResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseResponse> resourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new BaseResponse(false, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<BaseResponse> handleJwtException(JwtException ex) {
        return new ResponseEntity<>(new BaseResponse(false, ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(new BaseResponse(false, "Bad Request"), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse> handleUserAlreadyExistsException(CustomException ex) {
        return new ResponseEntity<>(new BaseResponse(false, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>(new BaseResponse(false, ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<BaseResponse> handleJwtAuthenticationException(JwtAuthenticationException ex) {
        return new ResponseEntity<>(new BaseResponse(false, ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }


}