package com.Kritiraj.SpringJPAHibernate.service;

import com.Kritiraj.SpringJPAHibernate.model.Customer;
import com.Kritiraj.SpringJPAHibernate.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
