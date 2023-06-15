package com.banking.accountmanagementapps.service.impl;

import com.banking.accountmanagementapps.dto.AccountDTO;
import com.banking.accountmanagementapps.entity.AccountEntity;
import com.banking.accountmanagementapps.entity.CustomerEntity;
import com.banking.accountmanagementapps.exception.BusinessException;
import com.banking.accountmanagementapps.exception.ErrorModel;
import com.banking.accountmanagementapps.repository.AccountRepository;
import com.banking.accountmanagementapps.repository.CustomerRepository;
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
        if(!customerEntityOptional.isPresent()){
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("CUSTOMER_ID_DOES_NOT_EXIST");
            errorModel.setMessage("Customer ID Doesn't Exist, Please Create New Customer Id");
            List<ErrorModel> errors = new ArrayList<>();
            errors.add(errorModel);
            throw new BusinessException(errors);
        }

        AccountEntity accountEntity = accountDTO.toEntity(customerEntityOptional.get());
        accountRepository.save(accountEntity);
        return accountDTO.fromEntity(accountEntity);
    }

    @Override
    public AccountDTO updateAccount(AccountDTO accountDTO, String accountNumber) {
        Optional<AccountEntity> optionalAccountEntity = accountRepository.findByAccountNumber(accountNumber);
        AccountDTO dto = null;
        if(optionalAccountEntity.isPresent()){
            AccountEntity pe = optionalAccountEntity.get();
            pe.setAccountNumber(accountDTO.getAccountNumber());
            pe.setAccountType(accountDTO.getAccountType());
            pe.setBalance(accountDTO.getBalance());
            dto = AccountDTO.fromEntity(pe);
            accountRepository.save(pe);
        }

        return dto;
    }

    @Override
    public void deleteAccount(Long id) {

    }

    @Override
    public List<AccountDTO> getAccountByCustomerId(Long customerId) {
        List<AccountEntity> listOfAccount = accountRepository.findAllByCustomerId(customerId);
        List<AccountDTO> accountList = new ArrayList<>();

        for(AccountEntity ce: listOfAccount){
            AccountDTO dto = AccountDTO.fromEntity(ce);
            accountList.add(dto);
        }
        return accountList;
    }
}
