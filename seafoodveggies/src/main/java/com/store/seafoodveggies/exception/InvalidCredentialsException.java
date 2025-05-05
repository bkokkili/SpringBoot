package com.store.seafoodveggies.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String invalidPassword) {
        super(invalidPassword);
    }
}
