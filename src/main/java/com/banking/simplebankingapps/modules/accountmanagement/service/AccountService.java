package com.banking.simplebankingapps.modules.accountmanagement.service;


import com.banking.simplebankingapps.modules.accountmanagement.dto.AccountDTO;

import java.util.List;

public interface AccountService {
    AccountDTO createAccount (AccountDTO accountDTO);
    AccountDTO updateAccount (AccountDTO accountDTO, String accountNumber);
    List<AccountDTO> getAccountByCustomerId (Long customerId);
    void deleteAccount(String accountNumber);
}
