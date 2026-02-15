package com.example.userservice.exceptions;

public class RoleAlreadyExistException extends RuntimeException {
    public RoleAlreadyExistException(String message){
        super(message);
    }
}
