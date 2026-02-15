package com.example.userservice.exceptions;

import jakarta.persistence.criteria.CriteriaBuilder;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException(String message){
        super(message);
    }
}
