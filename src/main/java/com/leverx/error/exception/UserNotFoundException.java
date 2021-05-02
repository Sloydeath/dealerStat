package com.leverx.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class describes exception when user not found
 *
 * @author Andrew Panas
 */

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User is not found")
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
