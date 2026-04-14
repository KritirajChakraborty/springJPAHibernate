package com.Kritiraj.SpringJPAHibernate.controller;

import com.Kritiraj.SpringJPAHibernate.dto.request.CabRequest;
import com.Kritiraj.SpringJPAHibernate.dto.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<CabResponse>> registerCab(@RequestBody CabRequest cabRequest,
                                                               @PathVariable("id") int driverId) {
        CabResponse cabResponse = cabService.registerCab(cabRequest,driverId);
        return ResponseEntity.ok(ApiResponse.success(cabResponse,200,"Cab Registered Successfully!"));
    }
    @PostMapping("/register/cab")
    public ResponseEntity<ApiResponse<Cab>> registerCabWithoutDriver(@RequestBody CabRequest cabRequest) {
        Cab cab = cabService.registerCabWithoutDriver(cabRequest);
        return ResponseEntity.ok(ApiResponse.success(cab,202,"Cab Registered Successfully v2!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCab(@PathVariable("id") int cabId) {
        String message = cabService.deleteCabIfNoDriverAssigned(cabId);
        return ResponseEntity.ok(ApiResponse.success(null,202,"Cab deleted Successfully!"));
    }

    @GetMapping("/utilization/{id}")
    public ResponseEntity<ApiResponse<CabUtilizationResponse>> getCabUtilization(@PathVariable("id") int cabId) {
            CabUtilizationResponse cabResponse = cabService.findCabUtilization(cabId);
            return new ResponseEntity<>(ApiResponse.success(cabResponse,202,"Cab Utilization Generated Successfully!"), HttpStatus.OK);
    }
}
