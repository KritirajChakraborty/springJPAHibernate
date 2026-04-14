package com.Kritiraj.SpringJPAHibernate.controller;


import com.Kritiraj.SpringJPAHibernate.dto.request.BookingRequest;
import com.Kritiraj.SpringJPAHibernate.dto.response.ApiResponse;
import com.Kritiraj.SpringJPAHibernate.dto.response.BookingResponse;
import com.Kritiraj.SpringJPAHibernate.model.Booking;
import com.Kritiraj.SpringJPAHibernate.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping("/booking-cab/{id}")
    public ResponseEntity<ApiResponse<BookingResponse>> bookCab(@RequestBody BookingRequest bookingRequest, @PathVariable("id") int customerId) {
        BookingResponse bookingResponse = bookingService.bookCab(bookingRequest,customerId);
        return new ResponseEntity<>(ApiResponse.success(bookingResponse,200,"Booking successfully created!"),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBookingByID(@PathVariable int id) {
        String deletedBooking = bookingService.deleteBookingById(id);
        return new ResponseEntity<>(ApiResponse.success(null,202,"Booking Successfully deleted!"),HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<Booking>> cancelBooking(@PathVariable("id") int bookingId) {
        Booking booking = bookingService.cancelBooking(bookingId);
        return new ResponseEntity<>(ApiResponse.success(booking,202,"Booking successfully cancelled!"), HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<Booking>> completeBooking(@PathVariable("id") int bookingId) {
        Booking booking = bookingService.completeBooking(bookingId);
        return new ResponseEntity<>(ApiResponse.success(booking,202,"Booking successfully Completed!"), HttpStatus.ACCEPTED);
    }



}
