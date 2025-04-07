package org.example.userauthservice.exceptions;

public class IncorrectTokenException extends RuntimeException {
    public IncorrectTokenException(String message) {
        super(message);
    }
}
