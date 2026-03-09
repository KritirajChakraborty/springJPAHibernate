package com.Kritiraj.SpringJPAHibernate.service;

import com.Kritiraj.SpringJPAHibernate.Enum.TripStatus;
import com.Kritiraj.SpringJPAHibernate.dto.request.BookingRequest;
import com.Kritiraj.SpringJPAHibernate.dto.response.BookingResponse;
import com.Kritiraj.SpringJPAHibernate.exception.CabNotAvailableException;
import com.Kritiraj.SpringJPAHibernate.exception.CustomerNotFoundException;
import com.Kritiraj.SpringJPAHibernate.model.Booking;
import com.Kritiraj.SpringJPAHibernate.model.Cab;
import com.Kritiraj.SpringJPAHibernate.model.Customer;
import com.Kritiraj.SpringJPAHibernate.model.Driver;
import com.Kritiraj.SpringJPAHibernate.repository.BookingRepository;
import com.Kritiraj.SpringJPAHibernate.repository.CabRepository;
import com.Kritiraj.SpringJPAHibernate.repository.CustomerRepository;
import com.Kritiraj.SpringJPAHibernate.repository.DriverRepository;
import com.Kritiraj.SpringJPAHibernate.transformer.BookingTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CabRepository cabRepository;
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    DriverRepository driverRepository;

    public BookingResponse bookCab(BookingRequest bookingRequest, int customerId) {

        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if(optionalCustomer.isEmpty()) {
            throw new CustomerNotFoundException("Invalid Customer ID for booking!");
        }
        Customer customer = optionalCustomer.get();

        Booking booking = BookingTransformer.bookingRequestToBooking(bookingRequest);
        booking.setTripStatus(TripStatus.ONGOING);

        Cab cab = cabRepository.findAvailableCab();
        if(cab == null) {
            throw new CabNotAvailableException("Sorry. No Cab Found!");
        }
        cab.setAvailable(false);
        cabRepository.save(cab);

        booking.setBillAmount(booking.getTripDistanceInKM() * cab.getPerKMRate());

        Booking savedBooking = bookingRepository.save(booking);
        List<Booking> bookingListOfCustomer = customer.getBookings();
        bookingListOfCustomer.add(savedBooking);
        customerRepository.save(customer);

        Driver driver = driverRepository.findByCabCabId(cab.getCabId());
        List<Booking> bookingListOfDriver = driver.getBookings();
        bookingListOfDriver.add(savedBooking);
        driverRepository.save(driver);

        return BookingTransformer.bookingToBookingResponse(savedBooking,customer,cab,driver);

    }
}
