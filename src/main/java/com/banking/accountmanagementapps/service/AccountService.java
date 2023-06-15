package com.banking.accountmanagementapps.service;

import com.banking.accountmanagementapps.dto.AccountDTO;

import java.util.List;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDTO);
    AccountDTO updateAccount(AccountDTO accountDTO,String accountNumber);
    void deleteAccount (String accountNumber);
    List<AccountDTO> getAccountByCustomerId(Long customerId);

}
