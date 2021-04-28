package com.leverx.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Role not found")
public class UserRoleNotFoundException extends RuntimeException{
    public UserRoleNotFoundException(Long id) {
        super("Couldn't find role " + id);
    }
}
