package com.Kritiraj.SpringJPAHibernate.controller;

import com.Kritiraj.SpringJPAHibernate.Enum.Gender;
import com.Kritiraj.SpringJPAHibernate.dto.request.CustomerRequest;
import com.Kritiraj.SpringJPAHibernate.dto.request.CustomerUpdateRequest;
import com.Kritiraj.SpringJPAHibernate.dto.response.CustomerResponse;
import com.Kritiraj.SpringJPAHibernate.dto.response.ErrorResponse;
import com.Kritiraj.SpringJPAHibernate.exception.CustomerNotFoundException;
import com.Kritiraj.SpringJPAHibernate.service.CustomerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    //this is old way. Better way is using lombok. @SLF4J
    //private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @PostMapping("/add")
    public ResponseEntity<CustomerResponse> addCustomer(@RequestBody CustomerRequest customerRequest) {

        CustomerResponse savedCustomerResponse =  customerService.addCustomer(customerRequest);
        return new ResponseEntity<>(savedCustomerResponse, HttpStatus.OK);
    }

    @GetMapping("/get/customer-id/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable("id") int customerId) {
        log.trace("Entering finding customer controller");
        CustomerResponse customerResponse = null;
        try {
            customerResponse =  customerService.getCustomer(customerId);
            log.trace("Exiting finding customer controller");
            return new ResponseEntity<CustomerResponse>(customerResponse,HttpStatus.OK);
        } catch (CustomerNotFoundException e) {
            //PROBLEM HERE IS WE HAVE TO WRITE SAME CODE TO CATCH EXCEPTION EVERYWHERE IN THIS CONTROLLER
//            log.error("There is an error while fetching customer data :",e);
//            ErrorResponse error = new ErrorResponse(LocalDateTime.now(),e.getMessage(),"Customer Not Found with ID " + customerId);
//            return new ResponseEntity<ErrorResponse>(error,HttpStatus.NOT_FOUND);

            //THERE WE REMOVE IT FROM HERE AND WRITE A PUBLIC CLASS BELOW DOING THE SAME
            return new ResponseEntity<ErrorResponse>(customerExceptionHandler(e),HttpStatus.NOT_FOUND);
        }
    }
    //get customer by gender
    @GetMapping("/get/gender")
    public ResponseEntity<?> getCustomerByGender(@RequestParam("gender") Gender gender) {
       try {
           List<CustomerResponse> customers = customerService.getCustomersByGender(gender);
           return new ResponseEntity<List<CustomerResponse>>(customers,HttpStatus.OK);
       } catch (CustomerNotFoundException e) {
           return new ResponseEntity<ErrorResponse>(customerExceptionHandler(e),HttpStatus.NOT_FOUND);
       }

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

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerResponse> updateCustomerData(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest,
                                                               @PathVariable("id") int customer_id) {
        CustomerResponse customerResponse = customerService.updateCustomerData(customerUpdateRequest, customer_id);
        return new ResponseEntity<>(customerResponse,HttpStatus.ACCEPTED);
    }

    public ErrorResponse customerExceptionHandler(CustomerNotFoundException e) {
        return new ErrorResponse(LocalDateTime.now(),e.getMessage());
    }

}
