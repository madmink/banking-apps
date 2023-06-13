package com.banking.accountmanagementapps.dto;

import com.banking.accountmanagementapps.entity.AccountEntity;
import com.banking.accountmanagementapps.entity.CustomerEntity;
import com.banking.accountmanagementapps.model.AccountType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {
    private Long id;
    private String accountNumber;
    private AccountType accountType;
    private Double balance;
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

    public AccountDTO fromEntity(AccountEntity accountEntity){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(accountEntity.getId());
        accountDTO.setAccountNumber(accountEntity.getAccountNumber());
        accountDTO.setBalance(accountEntity.getBalance());
        accountDTO.setAccountType(accountEntity.getAccountType());
        accountDTO.setCustomerId(accountEntity.getCustomer().getId());
        return accountDTO;

    }
}


