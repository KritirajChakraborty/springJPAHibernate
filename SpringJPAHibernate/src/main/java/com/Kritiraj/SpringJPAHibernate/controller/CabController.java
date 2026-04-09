package com.Kritiraj.SpringJPAHibernate.controller;

import com.Kritiraj.SpringJPAHibernate.dto.request.CabRequest;
import com.Kritiraj.SpringJPAHibernate.dto.response.CabResponse;
import com.Kritiraj.SpringJPAHibernate.dto.response.CabUtilizationResponse;
import com.Kritiraj.SpringJPAHibernate.model.Cab;
import com.Kritiraj.SpringJPAHibernate.service.CabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cab")
public class CabController {

    @Autowired
    CabService cabService;

    @PostMapping("/register/driver/{id}")
    public ResponseEntity<CabResponse> registerCab(@RequestBody CabRequest cabRequest,
                                                   @PathVariable("id") int driverId) {
        CabResponse cabResponse = cabService.registerCab(cabRequest,driverId);
        return ResponseEntity.ok(cabResponse);
    }
    @PostMapping("/register/cab")
    public ResponseEntity<Cab> registerCabWithoutDriver(@RequestBody CabRequest cabRequest) {
        Cab cab = cabService.registerCabWithoutDriver(cabRequest);
        return ResponseEntity.ok(cab);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCab(@PathVariable("id") int cabId) {
        String message = cabService.deleteCabIfNoDriverAssigned(cabId);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/utilization/{id}")
    public ResponseEntity<CabUtilizationResponse> getCabUtilization(@PathVariable("id") int cabId) {
            CabUtilizationResponse cabResponse = cabService.findCabUtilization(cabId);
            return new ResponseEntity<>(cabResponse, HttpStatus.OK);
    }
}
