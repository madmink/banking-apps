package com.banking.accountmanagementapps.service.impl;

import com.banking.accountmanagementapps.repository.AccountRepository;
import com.banking.accountmanagementapps.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;
}
