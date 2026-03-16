package com.Kritiraj.SpringJPAHibernate.controller;


import com.Kritiraj.SpringJPAHibernate.dto.request.BookingRequest;
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
    public ResponseEntity<BookingResponse> bookCab(@RequestBody BookingRequest bookingRequest, @PathVariable("id") int customerId) {
        BookingResponse bookingResponse = bookingService.bookCab(bookingRequest,customerId);
        return ResponseEntity.ok(bookingResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookingByID(@PathVariable int id) {
        String deletedBooking = bookingService.deleteBookingById(id);
        return new ResponseEntity<>(deletedBooking,HttpStatus.ACCEPTED);
    }



}
