package com.banking.accountmanagementapps.service.impl;

import com.banking.accountmanagementapps.dto.AccountDTO;
import com.banking.accountmanagementapps.entity.AccountEntity;
import com.banking.accountmanagementapps.entity.CustomerEntity;
import com.banking.accountmanagementapps.exception.BusinessException;
import com.banking.accountmanagementapps.exception.ErrorModel;
import com.banking.accountmanagementapps.repository.AccountRepository;
import com.banking.accountmanagementapps.repository.CustomerRepository;
import com.banking.accountmanagementapps.repository.TransactionRepository;
import com.banking.accountmanagementapps.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

@Autowired
private CustomerRepository customerRepository;

@Autowired
private AccountRepository accountRepository;

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Optional<CustomerEntity> customerEntityOptional = customerRepository.findById(accountDTO.getCustomerId());
        if(customerEntityOptional.isPresent()){
            AccountEntity accountEntity = accountDTO.toEntity(customerEntityOptional.get());
            accountRepository.save(accountEntity);
            return accountDTO.fromEntity(accountEntity);
        }
        ErrorModel errorModel = new ErrorModel();
        errorModel.setCode("CUSTOMER_ID_DOES_NOT_EXIST");
        errorModel.setMessage("Customer ID Doesn't Exist, Please Create New Customer Id");
        List<ErrorModel> errors = new ArrayList<>();
        errors.add(errorModel);
        throw new BusinessException(errors);

    }

    @Override
    public AccountDTO getAccountById(AccountDTO accountDTO) {
        return null;
    }

    @Override
    public AccountDTO updateAccount(AccountDTO accountDTO) {
        return null;
    }

    @Override
    public void deleteAccount(Long id) {

    }

    @Override
    public List<AccountDTO> getAccountByCustomerId(Long customerId) {
        return null;
    }
}
