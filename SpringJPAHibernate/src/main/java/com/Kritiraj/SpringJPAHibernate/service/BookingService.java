package com.Kritiraj.SpringJPAHibernate.service;

import com.Kritiraj.SpringJPAHibernate.Enum.TripStatus;
import com.Kritiraj.SpringJPAHibernate.dto.request.BookingRequest;
import com.Kritiraj.SpringJPAHibernate.dto.response.BookingResponse;
import com.Kritiraj.SpringJPAHibernate.dto.response.BookingsResponse;
import com.Kritiraj.SpringJPAHibernate.exception.*;
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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    JavaMailSender javaMailSender;

    public BookingResponse bookCab(BookingRequest bookingRequest, int customerId) {

        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if(optionalCustomer.isEmpty()) {
            throw new CustomerNotFoundException("Invalid Customer ID for booking!");
        }
        Customer customer = optionalCustomer.get();

        Cab cab = cabRepository.findAvailableCab();
        if(cab == null) {
            throw new CabNotAvailableException("Sorry. No Cab Found!");
        }
        cab.setAvailable(false);
        cabRepository.save(cab);

        Booking booking = BookingTransformer.bookingRequestToBooking(bookingRequest,cab.getPerKMRate());
        Booking savedBooking = bookingRepository.save(booking);
        customer.getBookings().add(savedBooking);

        Optional<Driver> driver = driverRepository.findDriverByCabId(cab.getCabId());
        if(driver.isEmpty()) {
            throw new CabNotAssociatedWithAnyDriverException("Sorry! Cab does not have a driver!");
        }
        Driver fetchedDriver = driver.get();
        fetchedDriver.getBookings().add(savedBooking);

        Customer savedCustomer = customerRepository.save(customer);
        Driver savedDriver = driverRepository.save(fetchedDriver);

        sendEmail(savedCustomer);
        return BookingTransformer.bookingToBookingResponse(savedBooking,savedCustomer,cab,savedDriver);

    }

    public String deleteBookingById(int id) {

        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException("Booking Not Found! Invalid ID"));
        if(booking.getTripStatus() == TripStatus.ONGOING) {
            throw new BookingDeletionException("Cannot Delete Booking! Trip ONGOING!");
        }
        bookingRepository.delete(booking);
        return "Booking with " + booking.getBookingId() + " successfully deleted!";
    }

    private void sendEmail(Customer customer) {

        String body = "Congrats " + customer.getName() + ". Your booking is gachi confirmed!";

        SimpleMailMessage simpleEmailMessage = new SimpleMailMessage();
        simpleEmailMessage.setFrom("kritiraj.exams2021@gmail.com");
        simpleEmailMessage.setTo(customer.getEmail());
        simpleEmailMessage.setSubject("Cab Booked.");
        simpleEmailMessage.setText(body);
        javaMailSender.send(simpleEmailMessage);
    }

    public List<BookingsResponse> getAllBookingsForCustomer(int customerId) {

        Customer customer = customerRepository.findById(customerId).orElse(null);

        if(customer == null) {
            throw new CustomerNotFoundException("Sorry! Cannot find bookings because customer doesnt exists!");
        }

        List<Booking> bookings = bookingRepository.findBookingsByCustomer(customerId);

        if(bookings.isEmpty()) {
            throw new BookingNotFoundException("Customer doesnt have bookings!!");
        }

        List<BookingsResponse> bookingsResponses = new ArrayList<>();

        for(Booking booking : bookings) {
            bookingsResponses.add(BookingTransformer.bookingToBookingsResponse(booking));
        }

        return bookingsResponses;
    }

    public List<BookingsResponse> getAllTripsForDriver(int driverId) {

        Driver driver = driverRepository.findById(driverId).orElse(null);

        if(driver == null) {
            throw new DriverNotFoundException("Sorry! Driver does not exist!");
        }

        List<Booking> trips = bookingRepository.findBookingsByDriver(driverId);

        if(trips.isEmpty()) {
            throw new BookingNotFoundException("Driver has not completed any trip!");
        }

        List<BookingsResponse> bookingsResponses = new ArrayList<>();

        for(Booking trip : trips) {
            bookingsResponses.add(BookingTransformer.bookingToBookingsResponse(trip));
        }

        return bookingsResponses;

    }
}
