package com.example.restdemo.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntityConflictException extends RuntimeException {
    public EntityConflictException() {
        super("Entity already exists");
    }
}
