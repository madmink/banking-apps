package com.banking.accountmanagementapps.service.impl;

import com.banking.accountmanagementapps.dto.CustomerDTO;
import com.banking.accountmanagementapps.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public CustomerDTO saveAccount(CustomerDTO customerDTO) {
        return null;
    }

    @Override
    public List<CustomerDTO> getAllAccountForUser(Long customerId) {
        return null;
    }

    @Override
    public CustomerDTO updateAccount(CustomerDTO customerDTO, Long customerId) {
        return null;
    }

    @Override
    public void deleteAccount(Long customerId) {

    }
}
