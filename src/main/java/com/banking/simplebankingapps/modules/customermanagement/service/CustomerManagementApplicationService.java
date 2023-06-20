package com.banking.simplebankingapps.modules.customermanagement.service;

import com.banking.simplebankingapps.api.dto.CustomerDTO;
import com.banking.simplebankingapps.modules.accountmanagement.domain.model.Account;
import com.banking.simplebankingapps.modules.accountmanagement.infrastructure.entity.AccountEntity;
import com.banking.simplebankingapps.modules.customermanagement.domain.model.Customer;
import com.banking.simplebankingapps.modules.customermanagement.domain.repository.CustomerRepository;
import com.banking.simplebankingapps.modules.accountmanagement.domain.repository.AccountRepository;
import com.banking.simplebankingapps.modules.customermanagement.exception.CustomerManagementException;
import com.banking.simplebankingapps.modules.customermanagement.infrastructure.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerManagementApplicationService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findByIdentityNumber(customerDTO.getIdentityNumber());
        if(optionalCustomerEntity.isPresent()) {
            throw new CustomerManagementException("IDENTITY_NUMBER_ALREADY_USED" , "Identity Number Already Used, contact customer service for assistance");
        }
        // Proceed to create a new customer
        Customer customer = customerDTO.toDomain();
        CustomerEntity customerEntity = customer.toEntityCustomer();
        CustomerEntity savedEntity = customerRepository.save(customerEntity);
        Customer savedCustomer = Customer.fromEntity(savedEntity);
        return CustomerDTO.fromDomain(savedCustomer);
    }


    public CustomerDTO getCustomerById(Long customerId) {
        CustomerEntity customerEntity = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerManagementException("CUSTOMER_ID_ERROR","Customer ID doesn't exist, please contact the Customer Service"));

        Customer customer = Customer.fromEntity(customerEntity);
        return CustomerDTO.fromDomain(customer);
    }


    public CustomerDTO updateCustomer(CustomerDTO customerDTO, Long customerId) {
        Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(customerId);
        if (!optionalCustomerEntity.isPresent()) {
            throw new CustomerManagementException("CUSTOMER_NOT_FOUND", "The requested customer was not found.");
        }

        Customer customer = customerDTO.toDomain();

        CustomerEntity customerEntity = optionalCustomerEntity.get();
        customerEntity.updateFromDomain(customer);

        CustomerEntity updatedEntity = customerRepository.save(customerEntity);

        Customer updatedCustomer = Customer.fromEntity(updatedEntity);
        return CustomerDTO.fromDomain(updatedCustomer);
    }

    public void deleteCustomer(Long customerId) {
        Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(customerId);

        CustomerEntity customerEntity = optionalCustomerEntity.orElseThrow(() ->
                new CustomerManagementException("CUSTOMER_ID_ERROR", "Customer Not Found, please check the Customer Id"));

        List<AccountEntity> accounts = accountRepository.findAllByCustomerId(customerId);

        if (!accounts.isEmpty()) {
            throw new CustomerManagementException("CUSTOMER_ID_ERROR", "Customer Still has active accounts. Please close the accounts first");
        }
        customerRepository.delete(customerEntity);
    }








}
