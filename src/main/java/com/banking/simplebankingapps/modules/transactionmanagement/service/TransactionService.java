package com.banking.simplebankingapps.modules.transactionmanagement.service;

import com.banking.simplebankingapps.modules.transactionmanagement.dto.TransactionDTO;

import java.math.BigDecimal;

public interface TransactionService {
    TransactionDTO deposit (String accountNumber, BigDecimal amount);
    TransactionDTO withdraw(String accountNumber, BigDecimal amount);
    TransactionDTO transfer(String fromAccountNumber, String toAccountNumber,BigDecimal amount);
}
