package com.Kritiraj.SpringJPAHibernate.service;

import com.Kritiraj.SpringJPAHibernate.dto.request.CustomerRequest;
import com.Kritiraj.SpringJPAHibernate.dto.response.CustomerResponse;
import com.Kritiraj.SpringJPAHibernate.exception.CustomerNotFoundException;
import com.Kritiraj.SpringJPAHibernate.model.Customer;
import com.Kritiraj.SpringJPAHibernate.repository.CustomerRepository;
import com.Kritiraj.SpringJPAHibernate.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    public CustomerResponse addCustomer(CustomerRequest customerRequest) {

        Customer customer = CustomerTransformer.customerRequestToCustomer(customerRequest);

        Customer savedCustomer = customerRepository.save(customer);

        return CustomerTransformer.customerToCustomerResponse(customer);
    }

    public CustomerResponse getCustomer(int customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if(optionalCustomer.isEmpty()) {
            throw new CustomerNotFoundException("Invalid ID, Customer doesn't exist!");
        }

        Customer savedCustomer = optionalCustomer.get();
        return CustomerTransformer.customerToCustomerResponse(savedCustomer);
    }
}
