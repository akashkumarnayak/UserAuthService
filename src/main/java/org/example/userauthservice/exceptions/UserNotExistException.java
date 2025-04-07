package org.example.userauthservice.exceptions;

public class UserNotExistException extends RuntimeException{
    public UserNotExistException(String message)
    {
        super(message);
    }
}
