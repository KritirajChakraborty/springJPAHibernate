package com.Kritiraj.SpringJPAHibernate.controller;

import com.Kritiraj.SpringJPAHibernate.dto.request.DriverRequest;
import com.Kritiraj.SpringJPAHibernate.dto.response.DriverResponse;
import com.Kritiraj.SpringJPAHibernate.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver")
public class DriverController {

    @Autowired
    DriverService driverService;

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
}
