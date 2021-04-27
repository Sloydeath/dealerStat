package com.leverx.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Email is not activated")
public class UserIsNotActiveException extends RuntimeException{
    public UserIsNotActiveException(String message) {
        super(message);
    }
}