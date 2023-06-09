package com.banking.accountmanagementapps.service;

import com.banking.accountmanagementapps.dto.AccountDTO;
import com.banking.accountmanagementapps.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO saveAccount(CustomerDTO customerDTO);
    List<CustomerDTO> getAllAccountForUser(Long customerId);
    CustomerDTO updateAccount (CustomerDTO customerDTO, Long customerId);
    void deleteAccount(Long customerId);
}
