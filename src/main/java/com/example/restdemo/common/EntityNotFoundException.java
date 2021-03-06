package com.example.restdemo.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such user")
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super("No such entity");
    }
}
