package com.Kritiraj.SpringJPAHibernate.transformer;

import com.Kritiraj.SpringJPAHibernate.dto.request.BookingRequest;
import com.Kritiraj.SpringJPAHibernate.dto.response.BookingResponse;
import com.Kritiraj.SpringJPAHibernate.dto.response.CabResponse;
import com.Kritiraj.SpringJPAHibernate.dto.response.CustomerResponse;
import com.Kritiraj.SpringJPAHibernate.dto.response.DriverResponse;
import com.Kritiraj.SpringJPAHibernate.model.Booking;
import com.Kritiraj.SpringJPAHibernate.model.Cab;
import com.Kritiraj.SpringJPAHibernate.model.Customer;
import com.Kritiraj.SpringJPAHibernate.model.Driver;

public class BookingTransformer {
    public static Booking bookingRequestToBooking (BookingRequest bookingRequest) {
        return Booking.builder()
                .pickup(bookingRequest.getPickup())
                .destination(bookingRequest.getDestination())
                .tripDistanceInKM(bookingRequest.getTripDistanceInKM())
                .build();
    }

    public static BookingResponse bookingToBookingResponse(Booking booking, Customer customer, Cab cab, Driver driver) {

        CustomerResponse customerResponse = CustomerTransformer.customerToCustomerResponse(customer);
        DriverResponse driverResponse = DriverTransformer.driverToDriverResponse(driver);
        CabResponse cabResponse = CabTransformer.cabToCabResponse(cab,driverResponse);

        return BookingResponse.builder()
                .pickup(booking.getPickup())
                .destination(booking.getDestination())
                .billAmount(booking.getBillAmount())
                .tripStatus(booking.getTripStatus())
                .tripDistanceInKM(booking.getTripDistanceInKM())
                .bookedAt(booking.getBookedAt())
                .lastUpdateAt(booking.getLastUpdateAt())
                .customer(customerResponse)
                .cab(cabResponse)
                .build();
    }
}
