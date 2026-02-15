package dev.manoj.productcatalog.controllers;

import dev.manoj.productcatalog.dtos.ErrorResponseDto;
import dev.manoj.productcatalog.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//ControllerAdvices advise all the controllers that the specific exception will be handled in this class.
@ControllerAdvice

public class ExceptionAdvices {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> notFoundExceptionResponse(){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto("Requested Item Not Found");
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }
}
