package com.example.userservice.exceptions;

import org.hibernate.engine.spi.EntityUniqueKey;

public class InvalidRoleException extends RuntimeException {
    public InvalidRoleException(String message){
        super(message);
    }
}
