package com.banking.accountmanagementapps.service;

import com.banking.accountmanagementapps.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO createCustomer(CustomerDTO customerDTO);
    List<CustomerDTO> getCustomerById(Long customerId);
    CustomerDTO updateCustomer(CustomerDTO customerDTO, Long customerId);
    void deleteCustomer(Long customerId);
}
