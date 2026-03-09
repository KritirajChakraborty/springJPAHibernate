package com.Kritiraj.SpringJPAHibernate.controller;

import com.Kritiraj.SpringJPAHibernate.Enum.Gender;
import com.Kritiraj.SpringJPAHibernate.dto.request.CustomerRequest;
import com.Kritiraj.SpringJPAHibernate.dto.response.CustomerResponse;
import com.Kritiraj.SpringJPAHibernate.model.Customer;
import com.Kritiraj.SpringJPAHibernate.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    //get customer by gender
    @GetMapping("/get/gender")
    public ResponseEntity<List<CustomerResponse>> getCustomerByGender(@RequestParam("gender") Gender gender) {
        List<CustomerResponse> customers = customerService.getCustomersByGender(gender);
        return new ResponseEntity<>(customers,HttpStatus.OK);
    }
    //get customer by gender and age
    @GetMapping("/get/gender-age")
    public ResponseEntity<List<CustomerResponse>> getCustomerByGenderAndAge(@RequestParam("gender") Gender gender,
                                                                            @RequestParam("age") int age) {
        List<CustomerResponse> customers = customerService.getCustomersByGenderAndAge(gender,age);
        return new ResponseEntity<>(customers,HttpStatus.OK);
    }
    //get customer by gender and age > some value
    @GetMapping("/get/gender-and-age-greater-than")
    public ResponseEntity<List<CustomerResponse>> getCustomerByGenderAndAgeGreaterThan(@RequestParam("gender") Gender gender,
                                                                            @RequestParam("age") int age) {
        List<CustomerResponse> customers = customerService.getCustomersByGenderAndAgeGreaterThan(gender,age);
        return new ResponseEntity<>(customers,HttpStatus.OK);
    }

}
