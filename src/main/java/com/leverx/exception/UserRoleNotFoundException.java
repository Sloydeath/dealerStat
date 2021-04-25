package com.leverx.exception;

public class UserRoleNotFoundException extends RuntimeException{
    public UserRoleNotFoundException(Long id) {
        super("Couldn't find role " + id);
    }
}
