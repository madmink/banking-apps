package com.banking.simplebankingapps.api.dto;

import com.banking.simplebankingapps.modules.accountmanagement.domain.model.Account;
import com.banking.simplebankingapps.modules.customermanagement.domain.model.Customer;
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
    private CustomerDTO customer;
    private AccountType accountType;

    public Account toDomain() {
        Customer customerDomain = null;
        if (this.customer != null) {
            customerDomain = this.customer.toDomain();
        }
        return new Account(this.id, this.accountNumber, this.balance, customerDomain, this.accountType, null);
    }

    public static AccountDTO fromDomain(Account account) {
        CustomerDTO customerDTO = null;
        if (account.getCustomer() != null) {
            customerDTO = CustomerDTO.fromDomain(account.getCustomer());
        }
        return new AccountDTO(account.getId(), account.getAccountNumber(), account.getBalance(), customerDTO, account.getAccountType());
    }
}
