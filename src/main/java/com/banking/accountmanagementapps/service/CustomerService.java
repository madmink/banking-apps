package com.banking.accountmanagementapps.service;

import com.banking.accountmanagementapps.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CustomerDTO getCustomerById(Long id);
    List<CustomerDTO> getAllAccountForCustomer(Long customerId);
    CustomerDTO updateCustomer(CustomerDTO customerDTO, Long customerId);
    void deleteCustomer(Long customerId);
}
