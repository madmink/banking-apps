package com.banking.simplebankingapps.modules.customermanagement.service;

import com.banking.simplebankingapps.modules.customermanagement.dto.CustomerDTO;
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

@Service
public class CustomerManagementServiceImpl implements CustomerManagementService, AccountProvider {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findByIdentityNumber(customerDTO.getIdentityNumber());
        if(optionalCustomerEntity.isPresent()) {
            throw new CustomerManagementException("IDENTITY_NUMBER_ALREADY_USED" , "Identity Number Already Used, contact customer service for assistance");
        }
        Customer customer = customerDTO.customerDTOtoCustomerDomain();
        CustomerEntity customerEntity = customer.toCustomerEntity();
        CustomerEntity savedEntity = customerRepository.save(customerEntity);
        Customer savedCustomer = Customer.fromCustomerEntity(savedEntity);
        return CustomerDTO.fromDomain(savedCustomer);
    }


    public CustomerDTO getCustomerById(Long customerId) {
        CustomerEntity customerEntity = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerManagementException("CUSTOMER_ID_DOES_NOT_EXIST","Customer ID doesn't exist, please contact the Customer Service"));

        Customer customer = Customer.fromCustomerEntity(customerEntity);
        return CustomerDTO.fromDomain(customer);
    }


    public CustomerDTO updateCustomer(CustomerDTO customerDTO, Long customerId) {
        Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(customerId);
        if (optionalCustomerEntity.isEmpty()) {
            throw new CustomerManagementException("CUSTOMER_NOT_FOUND", "The requested customer was not found.");
        }

        Customer customer = customerDTO.customerDTOtoCustomerDomain();

        CustomerEntity customerEntity = optionalCustomerEntity.get();
        customerEntity.updateFromDomain(customer);

        CustomerEntity updatedEntity = customerRepository.save(customerEntity);

        Customer updatedCustomer = Customer.fromCustomerEntity(updatedEntity);
        return CustomerDTO.fromDomain(updatedCustomer);
    }

    public void deleteCustomer(Long customerId) {
        Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(customerId);

        CustomerEntity customerEntity = optionalCustomerEntity.orElseThrow(() ->
                new CustomerManagementException("CUSTOMER_ID_NOT_FOUND_TO_DELETE", "Customer Not Found, please check the Customer Id"));

        List<AccountEntity> accounts = accountRepository.findAllByCustomerId(customerId);

        if (!accounts.isEmpty()) {
            throw new CustomerManagementException("CUSTOMER_STILL_HAVE_UNRESOLVED_ACCOUNT", "Customer Still has active accounts. Please close the accounts first");
        }
        customerRepository.delete(customerEntity);
    }


    @Override
    public AccountProviderDTO provideAccountById(Long customerId) {
        return null;
    }
}
