package com.leverx.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "There is an account with that email address")
public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        super("There is an account with that email address");
    }
}
