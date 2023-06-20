package com.banking.simplebankingapps.modules.accountmanagement.service;

import com.banking.simplebankingapps.api.dto.AccountDTO;
import com.banking.simplebankingapps.modules.accountmanagement.domain.model.Account;
import com.banking.simplebankingapps.modules.accountmanagement.domain.repository.AccountRepository;
import com.banking.simplebankingapps.modules.accountmanagement.exception.AccountManagementException;
import com.banking.simplebankingapps.modules.accountmanagement.infrastructure.entity.AccountEntity;
import com.banking.simplebankingapps.modules.customermanagement.domain.model.Customer;
import com.banking.simplebankingapps.modules.customermanagement.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class AccountManagementApplicationService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    public AccountDTO createAccount(AccountDTO accountDTO) {
        Customer customer = Customer.fromEntity(customerRepository.findById(accountDTO.getCustomerId())
                .orElseThrow(() -> new AccountManagementException("Customer ID Doesn't Exist, Please Create New Customer Id")));

        Account account = accountDTO.toDomain(customer);

        // convert to entity before saving
        AccountEntity accountEntity = account.toAccEntity();
        accountEntity = accountRepository.save(accountEntity);

        // convert back to domain for returning
        account = Account.fromAccEntity(accountEntity);

        return AccountDTO.fromDomain(account);
    }


    public AccountDTO updateAccount(AccountDTO accountDTO, String accountNumber) {
        Account account = Account.fromAccEntity(accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountManagementException("Account doesn't exist in our system, please contact customer service for further assistance")));

        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setAccountType(accountDTO.getAccountType());
        account.setBalance(accountDTO.getBalance());

        AccountEntity accountEntity = account.toAccEntity();
        accountRepository.save(accountEntity);

        return AccountDTO.fromDomain(account);
    }


    public void deleteAccount(String accountNumber) {
        Account account = Account.fromAccEntity(accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountManagementException("The Account Number Doesn't Exist")));

        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new AccountManagementException("The Account Has Balance, Account only can be deleted if Balance is 0");
        }

        accountRepository.delete(account.toAccEntity());
    }

    public List<AccountDTO> getAccountByCustomerId(Long customerId) {
        List<Account> accounts = accountRepository.findAllByCustomerId(customerId);
        List<AccountDTO> accountList = new ArrayList<>();

        for (Account account : accounts) {
            AccountDTO dto = AccountDTO.fromDomain(account);
            accountList.add(dto);
        }
        return accountList;
    }
}
