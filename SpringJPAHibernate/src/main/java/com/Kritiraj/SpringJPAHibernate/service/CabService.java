package com.Kritiraj.SpringJPAHibernate.service;

import com.Kritiraj.SpringJPAHibernate.dto.request.CabRequest;
import com.Kritiraj.SpringJPAHibernate.dto.response.CabResponse;
import com.Kritiraj.SpringJPAHibernate.exception.DriverNotFoundException;
import com.Kritiraj.SpringJPAHibernate.model.Cab;
import com.Kritiraj.SpringJPAHibernate.model.Driver;
import com.Kritiraj.SpringJPAHibernate.repository.DriverRepository;
import com.Kritiraj.SpringJPAHibernate.transformer.CabTransformer;
import com.Kritiraj.SpringJPAHibernate.transformer.DriverTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CabService {
    @Autowired
    DriverRepository driverRepository;
    public CabResponse registerCab(CabRequest cabRequest, int driverId) {

        Optional<Driver> optionalDriver = driverRepository.findById(driverId);
        if(optionalDriver.isEmpty()) {
            throw new DriverNotFoundException("Invalid ID! No driver found for cab!");
        }
        Driver driver = optionalDriver.get();
        Cab cab = CabTransformer.cabRequestToCab(cabRequest);
        driver.setCab(cab); // this cascading is on, saving the driver will auto save the cab also
        Driver savedDriver = driverRepository.save(driver);
        return CabTransformer.cabToCabResponse(savedDriver.getCab(), DriverTransformer.driverToDriverResponse(savedDriver));
    }
}
