package com.Kritiraj.SpringJPAHibernate.exception;

import com.Kritiraj.SpringJPAHibernate.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> customerExceptionHandler(CustomerNotFoundException e) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(),e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
