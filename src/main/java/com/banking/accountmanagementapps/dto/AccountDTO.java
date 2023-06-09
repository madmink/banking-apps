package com.banking.accountmanagementapps.dto;

import com.banking.accountmanagementapps.entity.AccountEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {
    private Long id;
    private String accountNumber;
    private Double balance;

    public AccountEntity toEntity(){
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(this.id);
        accountEntity.setAccountNumber(this.accountNumber);
        accountEntity.setBalance(this.balance);
        return accountEntity;
    }
}


