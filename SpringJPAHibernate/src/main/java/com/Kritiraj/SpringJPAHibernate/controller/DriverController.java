package com.Kritiraj.SpringJPAHibernate.controller;

import com.Kritiraj.SpringJPAHibernate.Enum.SortBy;
import com.Kritiraj.SpringJPAHibernate.dto.request.DriverRequest;
import com.Kritiraj.SpringJPAHibernate.dto.response.*;
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
    public ResponseEntity<ApiResponse<DriverResponse>> addDriver(@RequestBody DriverRequest driverRequest) {
        DriverResponse driverResponse = driverService.addDriver(driverRequest);
        return ResponseEntity.ok(ApiResponse.success(driverResponse,200,"Driver Registered Sucessfully!"));
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<DriverResponse>> getDriverById(@RequestParam("id") int driverId) {
        DriverResponse driverResponse = driverService.getDriverById(driverId);
        return ResponseEntity.ok(ApiResponse.success(driverResponse,200,"Driver Details Retirvied Successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDriverById(@PathVariable("id") int driverId) {
        String message = driverService.deleteDriverById(driverId);
        return ResponseEntity.ok(ApiResponse.success(null,202,"Driver Deleted Successfully!"));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<DriverResponse>>> getDriverBasedOnPagination(@RequestParam("page") int page,
                                                                           @RequestParam("size") int size,
                                                                           @RequestParam("sortBy") String sortBy,
                                                                           @RequestParam("direction") SortBy direction) {
        List<DriverResponse> drivers = driverService.getDriversBasedOnPagination(page,size,sortBy,direction);
        return new ResponseEntity<>(ApiResponse.success(drivers,202,"Drivers Retrieved Using Pagination!"), HttpStatus.OK);
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<ApiResponse<List<BookingsResponse>>> getTripsDoneByDriver(@PathVariable("id") int driverId) {
        List<BookingsResponse> trips = bookingService.getAllTripsForDriver(driverId);
        return new ResponseEntity<>(ApiResponse.success(trips,202,"Trips Retrieved By Driver"),HttpStatus.OK);
    }

    @GetMapping("/top")
    public ResponseEntity<ApiResponse<MostActiveDriverResponse>> getMostActiveDriver() {
        MostActiveDriverResponse driverResponse = driverService.getMostActiveDriver();
        return new ResponseEntity<>(ApiResponse.success(driverResponse,202,"Most Active Driver"),HttpStatus.OK);
    }

}
