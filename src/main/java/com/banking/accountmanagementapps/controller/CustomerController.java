package com.banking.accountmanagementapps.controller;

import com.banking.accountmanagementapps.dto.CustomerDTO;
import com.banking.accountmanagementapps.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customerDTO){
        CustomerDTO savedCustomerDTO = customerService.saveCustomer(customerDTO);
        return new ResponseEntity<>(savedCustomerDTO, HttpStatus.CREATED);
    }
}
