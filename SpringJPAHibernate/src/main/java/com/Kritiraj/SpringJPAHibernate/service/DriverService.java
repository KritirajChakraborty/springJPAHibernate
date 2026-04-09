package com.Kritiraj.SpringJPAHibernate.service;

import com.Kritiraj.SpringJPAHibernate.Enum.SortBy;
import com.Kritiraj.SpringJPAHibernate.dto.request.DriverRequest;
import com.Kritiraj.SpringJPAHibernate.dto.response.DriverResponse;
import com.Kritiraj.SpringJPAHibernate.exception.DriverDeletionException;
import com.Kritiraj.SpringJPAHibernate.exception.DriverNotFoundException;
import com.Kritiraj.SpringJPAHibernate.model.Driver;
import com.Kritiraj.SpringJPAHibernate.repository.DriverRepository;
import com.Kritiraj.SpringJPAHibernate.transformer.DriverTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    public String deleteDriverById(int driverId) {

        Driver driver = driverRepository.findById(driverId).orElseThrow(() -> new DriverNotFoundException(("Invalid ID! Cannot find Driver")));

        boolean cabAvailable = driver.getCab().isAvailable();
        if(!cabAvailable) {
            throw new DriverDeletionException("Cannot delete driver! Ride is going on!");
        }
        driverRepository.delete(driver);
        return "Driver Successfully Deleted!";
    }

    public List<DriverResponse> getDriversBasedOnPagination(int page, int size, String sortBy, SortBy direction)  {

        if(page < 0 || size <= 0 || size > 10)  {
            throw new IllegalArgumentException("Invalid Input. See page or size");
        }
        long totalDrivers = driverRepository.count();
        long totalPages = (totalDrivers + size - 1) / size;
        if(totalPages > 0 && page >= totalPages) {
            throw new IllegalArgumentException("Page Number Out Of Range!");
        }

        int limit = size;
        int offset = page*size;

        List<Driver> drivers = driverRepository.findDriversBasedOnPaginationWithNativeQuery(offset,limit,sortBy,direction.toString());

        if(drivers.isEmpty()) {
            return Collections.emptyList();
        }

        List<DriverResponse> driverResponses = new ArrayList<>();
        for(Driver driver : drivers) {
            driverResponses.add(DriverTransformer.driverToDriverResponse(driver));
        }
        return driverResponses;
    }

    public DriverResponse getMostActiveDriver() {

        Driver driver = driverRepository.findMostActiveDriver();
        return DriverTransformer.driverToDriverResponse(driver);

    }
}
