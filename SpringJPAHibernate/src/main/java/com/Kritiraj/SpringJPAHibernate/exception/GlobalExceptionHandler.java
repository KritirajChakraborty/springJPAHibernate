package com.Kritiraj.SpringJPAHibernate.exception;

import com.Kritiraj.SpringJPAHibernate.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomerNotFound(CustomerNotFoundException e) {
        return new ResponseEntity<>(
                ApiResponse.error(e.getMessage(), 404),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(CustomerDeletionException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomerDeletion(CustomerDeletionException e) {
        return new ResponseEntity<>(
                ApiResponse.error(e.getMessage(), 409),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(DriverDeletionException.class)
    public ResponseEntity<ApiResponse<Void>> handleDriverDeletion(DriverDeletionException e) {
        return new ResponseEntity<>(
                ApiResponse.error(e.getMessage(), 409),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(CabNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleCabNotFound(CabNotFoundException e) {
        return new ResponseEntity<>(
                ApiResponse.error(e.getMessage(), 404),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(CabDeletionException.class)
    public ResponseEntity<ApiResponse<Void>> handleCabDeletion(CabDeletionException e) {
        return new ResponseEntity<>(
                ApiResponse.error(e.getMessage(), 409),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(CabNotAssociatedWithAnyDriverException.class)
    public ResponseEntity<ApiResponse<Void>> handleCabAssociation(CabNotAssociatedWithAnyDriverException e) {
        return new ResponseEntity<>(
                ApiResponse.error(e.getMessage(), 403),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleBookingNotFound(BookingNotFoundException e) {
        return new ResponseEntity<>(
                ApiResponse.error(e.getMessage(), 404),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(BookingDeletionException.class)
    public ResponseEntity<ApiResponse<Void>> handleBookingDeletion(BookingDeletionException e) {
        return new ResponseEntity<>(
                ApiResponse.error(e.getMessage(), 409),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneral(Exception e) {
        return new ResponseEntity<>(
                ApiResponse.error("Something went wrong", 500),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}