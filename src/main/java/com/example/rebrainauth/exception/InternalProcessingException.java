package com.example.rebrainauth.exception;

public class InternalProcessingException extends RuntimeException{
    public InternalProcessingException(String reason) {
        super(reason);
    }
}
