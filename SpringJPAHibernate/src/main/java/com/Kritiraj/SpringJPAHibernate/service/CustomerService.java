package com.Kritiraj.SpringJPAHibernate.service;

import com.Kritiraj.SpringJPAHibernate.dto.request.CustomerRequest;
import com.Kritiraj.SpringJPAHibernate.dto.response.CustomerResponse;
import com.Kritiraj.SpringJPAHibernate.exception.CustomerNotFoundException;
import com.Kritiraj.SpringJPAHibernate.model.Customer;
import com.Kritiraj.SpringJPAHibernate.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    public CustomerResponse addCustomer(CustomerRequest customerRequest) {

        Customer customer = new Customer();
        customer.setName(customerRequest.getName());
        customer.setAge(customerRequest.getAge());
        customer.setEmail(customerRequest.getEmail());
        customer.setGender(customerRequest.getGender());

        Customer savedCustomer = customerRepository.save(customer);

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setName(savedCustomer.getName());
        customerResponse.setAge(savedCustomer.getAge());
        customerResponse.setEmail(savedCustomer.getEmail());
        return customerResponse;
    }

    public CustomerResponse getCustomer(int customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if(optionalCustomer.isEmpty()) {
            throw new CustomerNotFoundException("Invalid ID, Customer doesn't exist!");
        }

        Customer savedCustomer = optionalCustomer.get();
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setName(savedCustomer.getName());
        customerResponse.setAge(savedCustomer.getAge());
        customerResponse.setEmail(savedCustomer.getEmail());
        return customerResponse;
    }
}
