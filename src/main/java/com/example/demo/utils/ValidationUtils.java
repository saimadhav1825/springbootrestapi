package com.example.demo.utils;

public class ValidationUtils {

    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Implement your custom phone number validation logic here
        // Return true if the phone number is valid, otherwise false
        // You can use regular expressions or other validation methods
        return phoneNumber.matches("^(\\d{10}|\\d{11})$");
    }
}
