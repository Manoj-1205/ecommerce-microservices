package com.example.userservice.exceptions;

import com.example.userservice.models.User;

public class UserDoesNotExistException extends RuntimeException {
    public UserDoesNotExistException(String message){
        super(message);
    }
}
