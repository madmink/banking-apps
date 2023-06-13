package com.banking.accountmanagementapps.service.impl;

import com.banking.accountmanagementapps.dto.CustomerDTO;
import com.banking.accountmanagementapps.entity.CustomerEntity;
import com.banking.accountmanagementapps.repository.CustomerRepository;
import com.banking.accountmanagementapps.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerDTO createCustomer(@Valid CustomerDTO customerDTO) {
        CustomerEntity customerEntity = customerDTO.toEntity();
        CustomerEntity savedEntity = customerRepository.save(customerEntity);
        return CustomerDTO.fromEntity(savedEntity);
    }


    @Override
    public List<CustomerDTO> getCustomerById(Long customerId) {
        List<CustomerEntity> listOfCustomer = customerRepository.findAllById(customerId);
        List<CustomerDTO> customerList = new ArrayList<>();

        for(CustomerEntity ce: listOfCustomer){
            CustomerDTO dto = CustomerDTO.fromEntity(ce);
            customerList.add(dto);
        }
        return customerList;
    }
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO, Long customerId) {
        Optional<CustomerEntity>  optionalCustomerEntity = customerRepository.findById(customerId);
        CustomerDTO dto = null;
        if(optionalCustomerEntity.isPresent()){
            CustomerEntity pe = optionalCustomerEntity.get();
            pe.setId(customerDTO.getId());
            pe.setFirstName(customerDTO.getFirstName());
            pe.setLastName(customerDTO.getLastName());
            pe.setEmail(customerDTO.getEmail());
            pe.setAddress(customerDTO.getAddress());
            pe.setPhoneNumber(customerDTO.getPhoneNumber());
            dto= CustomerDTO.fromEntity(pe);
            customerRepository.save(pe);
        }
        return dto;
    }

    @Override
    public void deleteCustomer(Long customerId) {

    }
}
