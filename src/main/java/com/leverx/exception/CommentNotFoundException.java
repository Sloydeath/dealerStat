package com.leverx.exception;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(Long id) {
        super("Couldn't find comment " + id);
    }
}
