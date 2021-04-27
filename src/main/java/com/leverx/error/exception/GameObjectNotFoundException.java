package com.leverx.error.exception;

public class GameObjectNotFoundException extends RuntimeException {
    public GameObjectNotFoundException(Long id) {
        super("Couldn't find game object " + id);
    }
}
