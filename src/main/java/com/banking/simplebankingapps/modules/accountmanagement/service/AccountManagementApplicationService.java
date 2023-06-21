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

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    @Autowired
    public AccountManagementApplicationService(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    public AccountDTO createAccount(AccountDTO accountDTO) {
        Customer customer = Customer.fromEntity(customerRepository.findById(accountDTO.getCustomer().getId())
                .orElseThrow(() -> new AccountManagementException("Customer ID doesn't exist, please create a new Customer ID")));

        Account account = accountDTO.accountDtoToAccountDomain();
        account.setCustomer(customer);

        AccountEntity accountEntity = accountRepository.save(account.toAccEntity());

        return AccountDTO.fromAccountDomainToAccountDTO(Account.fromAccEntity(accountEntity));
    }


    public AccountDTO updateAccount(AccountDTO accountDTO, String accountNumber) {
        Account account = Account.fromAccEntity(accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountManagementException("Account doesn't exist in our system, please contact customer service for further assistance")));

        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setAccountType(accountDTO.getAccountType()); // Assign the AccountType enum directly
        account.setBalance(accountDTO.getBalance());

        AccountEntity accountEntity = account.toAccEntity();
        accountRepository.save(accountEntity);

        return AccountDTO.fromAccountDomainToAccountDTO(account);
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
        List<AccountEntity> accounts = accountRepository.findAllByCustomerId(customerId);
        List<AccountDTO> accountList = new ArrayList<>();

        for (AccountEntity account : accounts) {
            AccountDTO dto = AccountDTO.fromAccountDomainToAccountDTO(Account.fromAccEntity(account));
            accountList.add(dto);
        }
        return accountList;
    }
}
