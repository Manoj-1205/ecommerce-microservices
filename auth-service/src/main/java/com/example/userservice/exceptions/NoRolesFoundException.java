package com.example.userservice.exceptions;

public class NoRolesFoundException extends RuntimeException{
    public NoRolesFoundException(String message){
        super(message);
    }
}
