package com.leverx.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class describes exception when a user's email is not activated
 *
 * @author Andrew Panas
 */

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Email is not activated")
public class UserIsNotActiveException extends RuntimeException{
    public UserIsNotActiveException(String message) {
        super(message);
    }
}
