package com.banking.accountmanagementapps.service.impl;

import com.banking.accountmanagementapps.repository.AccountRepository;
import com.banking.accountmanagementapps.repository.CustomerRepository;
import com.banking.accountmanagementapps.repository.TransactionRepository;
import com.banking.accountmanagementapps.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

}
