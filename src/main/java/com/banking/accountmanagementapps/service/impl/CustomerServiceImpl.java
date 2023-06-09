package com.banking.accountmanagementapps.service.impl;

import com.banking.accountmanagementapps.dto.CustomerDTO;
import com.banking.accountmanagementapps.entity.AccountEntity;
import com.banking.accountmanagementapps.entity.CustomerEntity;
import com.banking.accountmanagementapps.repository.CustomerRepository;
import com.banking.accountmanagementapps.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = customerDTO.toEntity();
        CustomerEntity savedEntity = (CustomerEntity) customerRepository.save(customerEntity);
        return CustomerDTO.fromEntity(savedEntity);
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return null;
    }

    @Override
    public List<CustomerDTO> getAllAccountForCustomer(Long customerId) {
        return null;
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO, Long customerId) {
        return null;
    }

    @Override
    public void deleteCustomer(Long customerId) {

    }
}
