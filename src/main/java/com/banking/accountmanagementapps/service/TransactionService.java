package com.banking.accountmanagementapps.service;

import com.banking.accountmanagementapps.dto.TransactionDTO;

import java.math.BigDecimal;

public interface TransactionService {

    TransactionDTO deposit(String accountNumber, BigDecimal amount);
    TransactionDTO withdrawn(String accountNumber, BigDecimal amount);
    TransactionDTO transfer (String fromAccountNumber, String toAccountNumber, Double amount);
}
