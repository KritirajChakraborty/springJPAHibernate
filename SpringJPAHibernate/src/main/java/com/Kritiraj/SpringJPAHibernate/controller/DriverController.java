package com.Kritiraj.SpringJPAHibernate.controller;

import com.Kritiraj.SpringJPAHibernate.Enum.SortBy;
import com.Kritiraj.SpringJPAHibernate.dto.request.DriverRequest;
import com.Kritiraj.SpringJPAHibernate.dto.response.BookingResponse;
import com.Kritiraj.SpringJPAHibernate.dto.response.BookingsResponse;
import com.Kritiraj.SpringJPAHibernate.dto.response.DriverResponse;
import com.Kritiraj.SpringJPAHibernate.service.BookingService;
import com.Kritiraj.SpringJPAHibernate.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/driver")
public class DriverController {

    @Autowired
    DriverService driverService;

    @Autowired
    BookingService bookingService;

    @PostMapping("/add")
    public ResponseEntity<DriverResponse> addDriver(@RequestBody DriverRequest driverRequest) {
        DriverResponse driverResponse = driverService.addDriver(driverRequest);
        return ResponseEntity.ok(driverResponse);
    }

    @GetMapping("/get")
    public ResponseEntity<DriverResponse> getDriverById(@RequestParam("id") int driverId) {
        DriverResponse driverResponse = driverService.getDriverById(driverId);
        return ResponseEntity.ok(driverResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDriverbyId(@PathVariable("id") int driverId) {
        String message = driverService.deleteDriverById(driverId);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DriverResponse>> getDriverBasedOnPagination(@RequestParam("page") int page,
                                                                           @RequestParam("size") int size,
                                                                           @RequestParam("sortBy") String sortBy,
                                                                           @RequestParam("direction") SortBy direction) {
        List<DriverResponse> drivers = driverService.getDriversBasedOnPagination(page,size,sortBy,direction);
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<BookingsResponse>> getTripsDoneByDone(@PathVariable("id") int driverId) {
        List<BookingsResponse> trips = bookingService.getAllTripsForDriver(driverId);
        return new ResponseEntity<>(trips,HttpStatus.OK);
    }
}
