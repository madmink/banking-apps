package com.banking.simplebankingapps.api;

import com.banking.simplebankingapps.api.dto.CustomerDTO;
import com.banking.simplebankingapps.api.globalexceptionhandler.CustomErrorType;
import com.banking.simplebankingapps.modules.customermanagement.exception.CustomerManagementException;
import com.banking.simplebankingapps.modules.customermanagement.service.CustomerManagementApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerApi {

    private final CustomerManagementApplicationService customerService;

    @Autowired
    public CustomerApi(CustomerManagementApplicationService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createCustomer(@Valid @RequestBody CustomerDTO customerDto) {
        try {
            CustomerDTO createdCustomer = customerService.createCustomer(customerDto);
            return ResponseEntity.ok(createdCustomer);
        } catch (CustomerManagementException e) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add(e.getMessage());
            return new ResponseEntity<>(new CustomErrorType(e.getErrorCode(), errorMessages), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/detail/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable("id") Long customerId) {
        try {
            CustomerDTO customerDto = customerService.getCustomerById(customerId);
            return ResponseEntity.ok(customerDto);
        } catch (CustomerManagementException e) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add(e.getMessage());
            return new ResponseEntity<>(new CustomErrorType(e.getErrorCode(), errorMessages), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable("id") Long customerId,
                                                 @RequestBody CustomerDTO customerDto) {
        try {
            CustomerDTO updatedCustomerDto = customerService.updateCustomer(customerDto, customerId);
            return ResponseEntity.ok(updatedCustomerDto);
        } catch (CustomerManagementException e) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add(e.getMessage());
            return new ResponseEntity<>(new CustomErrorType(e.getErrorCode(), errorMessages), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long customerId) {
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
