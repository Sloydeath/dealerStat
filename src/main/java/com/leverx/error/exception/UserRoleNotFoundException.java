package com.leverx.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class describes exception when role not found
 *
 * @author Andrew Panas
 */

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Role not found")
public class UserRoleNotFoundException extends RuntimeException{
    public UserRoleNotFoundException() {
        super("Couldn't find role");
    }
}
