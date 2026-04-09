package com.Kritiraj.SpringJPAHibernate.service;

import com.Kritiraj.SpringJPAHibernate.dto.request.CabRequest;
import com.Kritiraj.SpringJPAHibernate.dto.response.CabResponse;
import com.Kritiraj.SpringJPAHibernate.dto.response.CabUtilizationResponse;
import com.Kritiraj.SpringJPAHibernate.exception.CabDeletionException;
import com.Kritiraj.SpringJPAHibernate.exception.CabNotFoundException;
import com.Kritiraj.SpringJPAHibernate.exception.DriverNotFoundException;
import com.Kritiraj.SpringJPAHibernate.model.Booking;
import com.Kritiraj.SpringJPAHibernate.model.Cab;
import com.Kritiraj.SpringJPAHibernate.model.Driver;
import com.Kritiraj.SpringJPAHibernate.repository.BookingRepository;
import com.Kritiraj.SpringJPAHibernate.repository.CabRepository;
import com.Kritiraj.SpringJPAHibernate.repository.DriverRepository;
import com.Kritiraj.SpringJPAHibernate.transformer.CabTransformer;
import com.Kritiraj.SpringJPAHibernate.transformer.DriverTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CabService {

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    CabRepository cabRepository;

    @Autowired
    BookingRepository bookingRepository;

    public CabResponse registerCab(CabRequest cabRequest, int driverId) {

        Optional<Driver> optionalDriver = driverRepository.findById(driverId);
        if (optionalDriver.isEmpty()) {
            throw new DriverNotFoundException("Invalid ID! No driver found for cab!");
        }
        Driver driver = optionalDriver.get();
        Cab cab = CabTransformer.cabRequestToCab(cabRequest);
        driver.setCab(cab); // this cascading is on, saving the driver will auto save the cab also
        Driver savedDriver = driverRepository.save(driver); //actually saving happens here
        return CabTransformer.cabToCabResponse(savedDriver.getCab(), DriverTransformer.driverToDriverResponse(savedDriver));
    }

    public Cab registerCabWithoutDriver(CabRequest cabRequest) {
        Cab cab = CabTransformer.cabRequestToCab(cabRequest);
        return cabRepository.save(cab);
    }


    public String deleteCabIfNoDriverAssigned(int cabId) {

        Cab cab = cabRepository.findById(cabId).orElseThrow(() -> new CabNotFoundException("Cab Not Found! Invalid Cab ID!"));
        Optional<Driver> optionalDriverForCab = driverRepository.findDriverByCabId(cabId);
        if (optionalDriverForCab.isPresent()) {
            throw new CabDeletionException("Cannot delete cab! Associated with a driver!");
        }

        cabRepository.delete(cab);
        return "Cab Deleted Successfully!";

    }

    public CabUtilizationResponse findCabUtilization(int cabId) {

        Cab cab = cabRepository.findById(cabId).orElse(null);

        if(cab == null) {
            throw new CabNotFoundException("Sorry! Cab Does Not Exist!");
        }

        Optional<Driver> optionalDriver = driverRepository.findDriverByCabId(cabId);

        if(optionalDriver.isEmpty()) {
            throw new DriverNotFoundException("No Driver Associated with this Cab");
        }

        Driver driver = optionalDriver.get();

        List<Booking> bookings = bookingRepository.findBookingsByDriver(driver.getDriverId());

        if(bookings.isEmpty()) {
            return CabUtilizationResponse.builder()
                    .cabId(cabId)
                    .cabModel(cab.getCabModel())
                    .cabNumber(cab.getCabNumber())
                    .totalDistance(0)
                    .totalRevenue(0)
                    .totalTrips(0)
                    .build();
        }

        int totalTrips = bookings.size();
        double totalDistance = 0;
        double totalRevenue = 0;

        for(Booking booking : bookings) {
            totalRevenue += booking.getBillAmount();
            totalDistance += booking.getTripDistanceInKM();
        }

        return CabUtilizationResponse.builder()
                .cabId(cabId)
                .cabNumber(cab.getCabNumber())
                .cabModel(cab.getCabModel())
                .totalTrips(totalTrips)
                .totalRevenue(totalRevenue)
                .totalDistance(totalDistance)
                .build();

    }
}
