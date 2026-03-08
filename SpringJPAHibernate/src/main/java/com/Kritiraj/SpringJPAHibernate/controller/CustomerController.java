package com.Kritiraj.SpringJPAHibernate.controller;

import com.Kritiraj.SpringJPAHibernate.dto.request.CustomerRequest;
import com.Kritiraj.SpringJPAHibernate.dto.response.CustomerResponse;
import com.Kritiraj.SpringJPAHibernate.model.Customer;
import com.Kritiraj.SpringJPAHibernate.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity<CustomerResponse> addCustomer(@RequestBody CustomerRequest customerRequest) {

        CustomerResponse savedCustomerResponse =  customerService.addCustomer(customerRequest);
        return new ResponseEntity<>(savedCustomerResponse, HttpStatus.OK);
    }

    @GetMapping("/get/customer-id/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable("id") int customerId) {
        CustomerResponse customerResponse =  customerService.getCustomer(customerId);
        return new ResponseEntity<>(customerResponse,HttpStatus.OK);
    }

}
