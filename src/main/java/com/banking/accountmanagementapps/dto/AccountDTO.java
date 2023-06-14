package com.banking.accountmanagementapps.dto;

import com.banking.accountmanagementapps.entity.AccountEntity;
import com.banking.accountmanagementapps.entity.CustomerEntity;
import com.banking.accountmanagementapps.model.AccountType;
import com.banking.accountmanagementapps.model.TransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {
    private Long id;
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private Long customerId;

    public AccountEntity toEntity(CustomerEntity customerEntity){
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(this.id);
        accountEntity.setAccountNumber(this.accountNumber);
        accountEntity.setBalance(this.balance);
        accountEntity.setAccountType(this.accountType);

        CustomerEntity customer = new CustomerEntity();
        customer.setId(this.customerId);
        accountEntity.setCustomer(customer);
        return accountEntity;
    }

    public static AccountDTO fromEntity(AccountEntity accountEntity){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(accountEntity.getId());
        accountDTO.setAccountNumber(accountEntity.getAccountNumber());
        accountDTO.setBalance(accountEntity.getBalance());
        accountDTO.setAccountType(accountEntity.getAccountType());
        accountDTO.setCustomerId(accountEntity.getCustomer().getId());
        return accountDTO;

    }
}


