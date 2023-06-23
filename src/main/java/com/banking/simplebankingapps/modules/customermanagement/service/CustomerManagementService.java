package com.banking.simplebankingapps.modules.customermanagement.service;
import com.banking.simplebankingapps.modules.customermanagement.dto.CustomerDTO;

public interface CustomerManagementService {

    CustomerDTO createCustomer (CustomerDTO customerDTO);
    CustomerDTO getCustomerById(Long customerId);
    CustomerDTO updateCustomer(CustomerDTO customerDTO, Long customerId);
    void deleteCustomer(Long customerId);

}
