package com.banking.simplebankingapps.modules.accountmanagement.service;

import com.banking.simplebankingapps.modules.accountmanagement.dto.AccountDTO;
import com.banking.simplebankingapps.modules.accountmanagement.domain.model.Account;
import com.banking.simplebankingapps.modules.accountmanagement.domain.repository.AccountRepository;
import com.banking.simplebankingapps.modules.accountmanagement.exception.AccountManagementException;
import com.banking.simplebankingapps.modules.accountmanagement.infrastructure.entity.AccountEntity;
import com.banking.simplebankingapps.modules.customermanagement.domain.repository.CustomerRepository;
import com.banking.simplebankingapps.modules.customermanagement.infrastructure.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class AccountManagementService implements AccountService{

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    @Autowired
    public AccountManagementService(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    public AccountDTO createAccount(AccountDTO accountDTO) {
        Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(accountDTO.getCustomerId());
        if (optionalCustomerEntity.isEmpty()) {
            throw new AccountManagementException("ACCOUNT_ERROR_CUSTOMER_DOES_NOT_EXIST", "Customer Does Not Exist, please contact customer service");
        }

        CustomerEntity customerEntity = optionalCustomerEntity.get();

        Account account = accountDTO.toAccountDomain(customerEntity);

        AccountEntity accountEntity = accountRepository.save(account.toAccountEntity());

        return AccountDTO.fromAccountDomainToAccountDTO(Account.fromAccountEntity(accountEntity));
    }


    public AccountDTO updateAccount(AccountDTO accountDTO, String accountNumber) {
        Account account = Account.fromAccountEntity(accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountManagementException("ACCOUNT_NOT_FOUND","Account doesn't exist in our system, please contact customer service for further assistance")));

        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setAccountType(accountDTO.getAccountType()); // Assign the AccountType enum directly
        account.setBalance(accountDTO.getBalance());

        AccountEntity accountEntity = account.toAccountEntity();
        accountRepository.save(accountEntity);

        return AccountDTO.fromAccountDomainToAccountDTO(account);
    }

    public List<AccountDTO> getAccountByCustomerId(Long customerId) {
        List<AccountEntity> accounts = accountRepository.findAllByCustomerId(customerId);
        List<AccountDTO> accountList = new ArrayList<>();

        for (AccountEntity account : accounts) {
            AccountDTO dto = AccountDTO.fromAccountDomainToAccountDTO(Account.fromAccountEntity(account));
            accountList.add(dto);
        }
        return accountList;
    }

    public void deleteAccount(String accountNumber) {
        Account account = Account.fromAccountEntity(accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountManagementException("ACCOUNT_NOT_FOUND","The Account Number Doesn't Exist")));

        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new AccountManagementException("DELETE_ACCOUNT_CRITERIA_NOT_MET","The Account Has Balance, Account only can be deleted if Balance is 0");
        }

        accountRepository.delete(account.toAccountEntity());
    }
}

