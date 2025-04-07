package org.example.userauthservice.exceptions;

public class UserCredentialsIncorrectException extends RuntimeException {
    public UserCredentialsIncorrectException(String message) {
        super(message);
    }
}
