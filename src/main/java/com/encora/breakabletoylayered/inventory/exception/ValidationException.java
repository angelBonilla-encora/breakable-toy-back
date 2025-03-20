package com.encora.breakabletoylayered.inventory.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends CustomException {
    public ValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
