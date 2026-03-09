package com.Kritiraj.SpringJPAHibernate.controller;

import com.Kritiraj.SpringJPAHibernate.dto.request.CabRequest;
import com.Kritiraj.SpringJPAHibernate.dto.response.CabResponse;
import com.Kritiraj.SpringJPAHibernate.service.CabService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
