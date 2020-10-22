package com.example.restdemo.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserConflictException extends RuntimeException {
    public UserConflictException() {
        super("User already exists");
    }
}
