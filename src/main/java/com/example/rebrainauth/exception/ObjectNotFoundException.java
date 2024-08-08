package com.example.rebrainauth.exception;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String reason) {
        super(reason);
    }
}
