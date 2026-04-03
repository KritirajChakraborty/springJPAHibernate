package com.Kritiraj.SpringJPAHibernate.service;

import com.Kritiraj.SpringJPAHibernate.Enum.Gender;
import com.Kritiraj.SpringJPAHibernate.Enum.SortBy;
import com.Kritiraj.SpringJPAHibernate.dto.request.CustomerRequest;
import com.Kritiraj.SpringJPAHibernate.dto.request.CustomerUpdateRequest;
import com.Kritiraj.SpringJPAHibernate.dto.response.CustomerResponse;
import com.Kritiraj.SpringJPAHibernate.exception.CustomerDeletionException;
import com.Kritiraj.SpringJPAHibernate.exception.CustomerNotFoundException;
import com.Kritiraj.SpringJPAHibernate.model.Customer;
import com.Kritiraj.SpringJPAHibernate.repository.BookingRepository;
import com.Kritiraj.SpringJPAHibernate.repository.CustomerRepository;
import com.Kritiraj.SpringJPAHibernate.transformer.CustomerTransformer;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
@Slf4j
@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    MeterRegistry meterRegistry;

    @Autowired
    BookingRepository bookingRepository;

    public CustomerResponse addCustomer(CustomerRequest customerRequest) {

        Customer customer = CustomerTransformer.customerRequestToCustomer(customerRequest);

        Customer savedCustomer = customerRepository.save(customer);
        meterRegistry.counter("custom.metric.numberOfCustomer").increment();

        return CustomerTransformer.customerToCustomerResponse(savedCustomer);
    }

    public CustomerResponse getCustomer(int customerId) {
        log.trace("Entered getCustomer service with customer id! {}", customerId);
//        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
//        if(optionalCustomer.isEmpty()) {
//            log.error("Cannot find customer at getCustomer Service");
//            throw new CustomerNotFoundException("Invalid ID, Customer doesn't exist!");
//        }
        //THIS IS MODERN WAY
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> {
            log.error("Cannot find customer at getCustomer Service");
            return new CustomerNotFoundException("Invalid ID! Customer not found");

        });

        //Customer savedCustomer = optionalCustomer.get();
        log.trace("Exited getCustomer service");
        return CustomerTransformer.customerToCustomerResponse(customer);
    }

    public List<CustomerResponse> getCustomersByGender(Gender gender) {
        List<Customer> customers = customerRepository.findByGender(gender);
        if(customers.isEmpty()) {
            throw new CustomerNotFoundException("Cannot find customers with gender ");
        }
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for(Customer customer : customers) {
            customerResponses.add(CustomerTransformer.customerToCustomerResponse(customer));
        }
        return customerResponses;
    }

    public List<CustomerResponse> getCustomersByGenderAndAge(Gender gender, int age) {
        List<Customer> customers = customerRepository.findByGenderAndAge(gender,age);
        if(customers.isEmpty()) {
            throw new CustomerNotFoundException("Cannot find customer with these details ");
        }
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for(Customer customer : customers) {
            customerResponses.add(CustomerTransformer.customerToCustomerResponse(customer));
        }
        return customerResponses;
    }

    public List<CustomerResponse> getCustomersByGenderAndAgeGreaterThan(Gender gender, int age) {
        List<Customer> customers = customerRepository.findByGenderAndAgeGreaterThan(gender,age);
        if(customers.isEmpty()) {
            throw new CustomerNotFoundException("Cannot find customers with ");
        }
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for(Customer customer : customers) {
            customerResponses.add(CustomerTransformer.customerToCustomerResponse(customer));
        }
        return customerResponses;
    }

    public CustomerResponse updateCustomerData(CustomerUpdateRequest customerUpdateRequest, int customerId) {

        //new way of checking if something is not there is DB
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Invalid Customer ID! Cant update!"));

        if(customerUpdateRequest.getName() != null) {
            customer.setName((customerUpdateRequest.getName()));
        }
        if(customerUpdateRequest.getAge() != null) {
            customer.setAge((customerUpdateRequest.getAge()));
        }
        if(customerUpdateRequest.getEmail() != null) {
            customer.setEmail((customerUpdateRequest.getEmail()));
        }
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerTransformer.customerToCustomerResponse(savedCustomer);
    }

    public String deleteCustomerById(int customerId) {

        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Invalid ID! Cannot delete customer"));

        Long bookingsOfCustomer = bookingRepository.existsByCustomerId(customer.getCustomerId());

        if(bookingsOfCustomer > 0) {
            throw new CustomerDeletionException("Sorry! Cant delete customer. Already in a ride!");
        }

        customerRepository.deleteById(customerId);
        return "Customer Deleted SuccessFully!";

    }

    public List<CustomerResponse> getCustomerBasedOnPagination(int page, int size, String sortBy, SortBy direction)  {


        if(page < 0 || size <= 0 || size > 10 ) {
            throw new IllegalArgumentException("Invalid Input. See page or size");
        }

        Long totalCustomers = customerRepository.count();
        Long totalPages = (totalCustomers + size - 1) / size;
        if(totalPages > 0 && page >= totalPages) {
            throw new IllegalArgumentException("Page number out of range");
        }

        Pageable pageable = PageRequest.of(page,
                                            size,
                                            Sort.by(Sort.Direction.fromString(direction.toString()), sortBy));
        //List<Customer> customers = customerRepository.findAll(pageable).getContent(); //findall is derived
        // ABOVE IS DERIVED QUERY, BELOW IS CUSTOM QUERY
         List<Customer> customers = customerRepository.findBasedOnPagination(pageable); //SEE REPO NOW sL-1

        if(customers.isEmpty()) {
            return Collections.emptyList();
        }
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for(Customer customer : customers) {
            CustomerResponse customerResponse = CustomerTransformer.customerToCustomerResponse(customer);
            customerResponses.add(customerResponse);
        }
        return customerResponses;
    }
}
