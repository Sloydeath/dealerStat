package com.leverx.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class describes exception when Game Object not found
 *
 * @author Andrew Panas
 */

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Game object not found")
public class GameObjectNotFoundException extends RuntimeException {
    public GameObjectNotFoundException(String message) {
        super(message);
    }
}
