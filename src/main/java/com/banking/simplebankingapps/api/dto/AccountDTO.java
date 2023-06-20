package com.banking.simplebankingapps.api.dto;

import com.banking.simplebankingapps.modules.accountmanagement.domain.model.Account;
import com.banking.simplebankingapps.modules.customermanagement.domain.model.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class AccountDTO {
    private Long id;
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private Long customerId;

    public Account toDomain(Customer customer){
        Account account = new Account();
        account.setId(this.id);
        account.setAccountNumber(this.accountNumber);
        account.setBalance(this.balance);
        account.setCustomer(customer);
        account.setAccountType(this.accountType); // Add this line
        return account;
    }

    public static AccountDTO fromDomain(Account account){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setAccountNumber(account.getAccountNumber());
        accountDTO.setBalance(account.getBalance());
        accountDTO.setCustomerId(account.getCustomer().getId());
        accountDTO.setAccountType(account.getAccountType());  // Add this line
        return accountDTO;
    }

    }

