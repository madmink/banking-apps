package com.banking.accountmanagementapps.controller;

import com.banking.accountmanagementapps.dto.CustomerDTO;
import com.banking.accountmanagementapps.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer")
public class CustomerController {
    @Autowired
    public CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO){
        CustomerDTO savedCustomerDTO = customerService.createCustomer(customerDTO);
        return new ResponseEntity<>(savedCustomerDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<CustomerDTO>> getCustomerById(@PathVariable("customerId") Long customerId){
        List<CustomerDTO> customerDTOList = customerService.getCustomerById(customerId);
        return new ResponseEntity<>(customerDTOList, HttpStatus.OK);
    }

    @PutMapping("update/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable Long customerId){
    CustomerDTO updateCustomerDTO = customerService.updateCustomer(customerDTO, customerId);
        return new ResponseEntity<>(updateCustomerDTO, HttpStatus.OK);
    }

    @DeleteMapping("delete/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId){
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>("Customer Has Been Deleted Successfully", HttpStatus.OK);
        }
    }

