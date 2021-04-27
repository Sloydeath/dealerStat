package com.leverx.error.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        super("There is an account with that email address");
    }
}
