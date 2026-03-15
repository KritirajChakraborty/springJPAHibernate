package com.Kritiraj.SpringJPAHibernate.service;

import com.Kritiraj.SpringJPAHibernate.Enum.Gender;
import com.Kritiraj.SpringJPAHibernate.dto.request.CustomerRequest;
import com.Kritiraj.SpringJPAHibernate.dto.request.CustomerUpdateRequest;
import com.Kritiraj.SpringJPAHibernate.dto.response.CustomerResponse;
import com.Kritiraj.SpringJPAHibernate.exception.CustomerNotFoundException;
import com.Kritiraj.SpringJPAHibernate.model.Customer;
import com.Kritiraj.SpringJPAHibernate.repository.CustomerRepository;
import com.Kritiraj.SpringJPAHibernate.transformer.CustomerTransformer;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    MeterRegistry meterRegistry;

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
}
