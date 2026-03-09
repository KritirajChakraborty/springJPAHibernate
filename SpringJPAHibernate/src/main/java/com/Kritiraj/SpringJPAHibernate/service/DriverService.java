package com.Kritiraj.SpringJPAHibernate.service;

import com.Kritiraj.SpringJPAHibernate.dto.request.DriverRequest;
import com.Kritiraj.SpringJPAHibernate.dto.response.DriverResponse;
import com.Kritiraj.SpringJPAHibernate.exception.DriverNotFoundException;
import com.Kritiraj.SpringJPAHibernate.model.Driver;
import com.Kritiraj.SpringJPAHibernate.repository.DriverRepository;
import com.Kritiraj.SpringJPAHibernate.transformer.DriverTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DriverService {

    @Autowired
    DriverRepository driverRepository;
    public DriverResponse addDriver(DriverRequest driverRequest) {

        Driver driver = DriverTransformer.driverRequestToDriver(driverRequest);
        Driver savedDriver = driverRepository.save(driver);
        return DriverTransformer.driverToDriverResponse(savedDriver);

    }

    public DriverResponse getDriverById(int driverId) {

        Optional<Driver> driver = driverRepository.findById(driverId);
        if(driver.isEmpty()) {
            throw new DriverNotFoundException("Invalid Driver ID!");
        }
        Driver savedDriver = driver.get();
        return DriverTransformer.driverToDriverResponse(savedDriver);

    }
}
