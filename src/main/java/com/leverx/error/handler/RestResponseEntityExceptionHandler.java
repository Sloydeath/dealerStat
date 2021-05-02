package com.leverx.error.handler;

import com.leverx.error.ApiError;
import com.leverx.error.exception.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

/**
 * This class handlers exceptions of any type
 *
 * @author Andrew Panas
 */

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = Logger.getLogger(RestResponseEntityExceptionHandler.class);

    @Autowired
    public RestResponseEntityExceptionHandler() {
        super();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.debug("400 Status Code", ex);
        ApiError apiError = new ApiError();
        apiError.setMessage(ex.getMessage());
        apiError.setErrors(ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList()));
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    // 400
    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.debug("400 Status Code", ex);
        ApiError apiError = new ApiError();
        apiError.setMessage(String.format("Invalid format: %s", ex.getMessage()));
        apiError.setErrors(ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList()));
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    // 404
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(final RuntimeException ex, final WebRequest request) {
        log.debug("404 Status Code", ex);
        String bodyOfResponse = "User not found";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    // 404
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<Object> handleCommentNotFound(final RuntimeException ex, final WebRequest request) {
        log.debug("404 Status Code", ex);
        String bodyOfResponse = "Comment not found";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    // 404
    @ExceptionHandler(GameObjectNotFoundException.class)
    public ResponseEntity<Object> handleGameObjectNotFound(final RuntimeException ex, final WebRequest request) {
        log.debug("404 Status Code", ex);
        String bodyOfResponse = "Game object not found";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    // 404
    @ExceptionHandler(UserRoleNotFoundException.class)
    public ResponseEntity<Object> handleUserRoleNotFound(final RuntimeException ex, final WebRequest request) {
        log.debug("404 Status Code", ex);
        String bodyOfResponse = "Role not found";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    // 409
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Object> handleUserAlreadyExist(final RuntimeException ex, final WebRequest request) {
        log.debug("409 Status Code", ex);
        String bodyOfResponse = "User with such email already exists";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    // 403
    @ExceptionHandler(UserIsNotActiveException.class)
    public ResponseEntity<Object> handleUserIsNotActive(final RuntimeException ex, final WebRequest request) {
        log.debug("403 Status Code", ex);
        String bodyOfResponse = "Account is not active. Please, go to your email and activate";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    // 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
        log.debug("500 Status Code", ex);
        ApiError apiError = new ApiError();
        apiError.setMessage("Internal error: " + ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
