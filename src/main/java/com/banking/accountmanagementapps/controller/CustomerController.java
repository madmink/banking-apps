package com.banking.accountmanagementapps.controller;

import com.banking.accountmanagementapps.dto.CustomerDTO;
import com.banking.accountmanagementapps.service.CustomerService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
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
    public ResponseEntity<CustomerDTO> saveCustomer(@Valid @RequestBody CustomerDTO customerDTO){
        CustomerDTO savedCustomerDTO = customerService.createCustomer(customerDTO);
        return new ResponseEntity<>(savedCustomerDTO, HttpStatus.CREATED);
    }

    @GetMapping("/getUser/{customerId}")
    public ResponseEntity<List<CustomerDTO>> getCustomerById(@PathVariable("customerId") Long customerId){
        List<CustomerDTO> customerDTOList = customerService.getCustomerById(customerId);
        ResponseEntity<List<CustomerDTO>> responseEntity = new ResponseEntity<>(customerDTOList, HttpStatus.OK);
        return responseEntity;
    }

    @PutMapping("update/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable Long customerId){
    CustomerDTO updateCustomerDTO = customerService.updateCustomer(customerDTO, customerId);
    ResponseEntity<CustomerDTO> responseEntity = new ResponseEntity<>(customerDTO, HttpStatus.OK);
    return responseEntity;
    }

}
