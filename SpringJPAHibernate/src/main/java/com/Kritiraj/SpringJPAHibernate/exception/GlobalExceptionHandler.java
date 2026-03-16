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
    public ResponseEntity<ErrorResponse> customerExceptionHandler(CustomerNotFoundException e) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(),e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CustomerDeletionException.class)
    public ResponseEntity<ErrorResponse> handleCustomerDeletionHandler(CustomerDeletionException e) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(),e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DriverDeletionException.class)
    public ResponseEntity<ErrorResponse> handleDriverDeletionHandler(DriverDeletionException e) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(),e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CabNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCabExceptionHandler(CabNotFoundException e) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(),e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CabDeletionException.class)
    public ResponseEntity<ErrorResponse> handleCabDeletionHandler(CabDeletionException e) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(),e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CabNotAssociatedWithAnyDriverException.class)
    public ResponseEntity<ErrorResponse> handleCabExceptionHandler(CabNotAssociatedWithAnyDriverException e) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(),e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookingNotFoundException(BookingNotFoundException e) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(),e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BookingDeletionException.class)
    public ResponseEntity<ErrorResponse> handleBookingDeletionException(BookingDeletionException e) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(),e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
    //GENERIC EXCEPTION HANDLER TO CATCH UNEXPECTED ERRORS
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(),e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
