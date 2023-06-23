package com.banking.simplebankingapps.modules.accountmanagement.dto;

import com.banking.simplebankingapps.modules.accountmanagement.domain.model.Account;
import com.banking.simplebankingapps.modules.customermanagement.domain.model.Customer;
import com.banking.simplebankingapps.modules.customermanagement.infrastructure.entity.CustomerEntity;
import com.banking.simplebankingapps.shared.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private Long customerId;
    private AccountType accountType;

    public Account toAccountDomain(CustomerEntity customerEntity) {
        Account account = new Account();
        account.setId(this.id);
        account.setAccountNumber(this.accountNumber);
        account.setAccountType(this.accountType);
        account.setBalance(this.balance);
        account.setCustomer(Customer.fromCustomerEntity(customerEntity));
        return account;
    }

    public static AccountDTO fromAccountDomainToAccountDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setAccountNumber(account.getAccountNumber());
        accountDTO.setAccountType(account.getAccountType());
        accountDTO.setBalance(account.getBalance());
        accountDTO.setCustomerId(account.getCustomer().getId());
        return accountDTO;

    }
}
