package com.store.seafoodveggies.exception;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String message){
        super(message);
    }
}
