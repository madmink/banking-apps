package com.banking.accountmanagementapps.service.impl;

import com.banking.accountmanagementapps.dto.TransactionDTO;
import com.banking.accountmanagementapps.entity.AccountEntity;

import com.banking.accountmanagementapps.entity.TransactionEntity;
import com.banking.accountmanagementapps.exception.BusinessException;
import com.banking.accountmanagementapps.exception.ErrorModel;
import com.banking.accountmanagementapps.model.TransactionType;
import com.banking.accountmanagementapps.repository.AccountRepository;
import com.banking.accountmanagementapps.repository.TransactionRepository;
import com.banking.accountmanagementapps.service.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;


@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public TransactionDTO deposit(String accountNumber, BigDecimal amount) {
        Optional<AccountEntity> optionalAccountEntity = accountRepository.findByAccountNumber(accountNumber);
        if (optionalAccountEntity.isEmpty()) {
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("ACCOUNT_NUMBER_DOES_NOT_EXIST");
            errorModel.setMessage("Account Doesn't Exist, please create new account to deposit");
            throw new BusinessException(Collections.singletonList(errorModel));
        }
        AccountEntity accountEntity = optionalAccountEntity.get();
        BigDecimal newBalance = accountEntity.getBalance().add(amount);
        accountEntity.setBalance(newBalance);
        accountRepository.save(accountEntity);

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setAccount(accountEntity);
        transactionEntity.setAmount(amount);
        transactionEntity.setTransactionType(TransactionType.DEPOSIT.name());
        transactionEntity.setDate(LocalDate.now());
        transactionRepository.save(transactionEntity);

        return TransactionDTO.fromEntity(transactionEntity);
    }

    @Override
    @Transactional
    public TransactionDTO withdrawn(String accountNumber, BigDecimal amount) {
        Optional<AccountEntity> optionalAccountEntity = accountRepository.findByAccountNumber(accountNumber);

        if (optionalAccountEntity.isEmpty()) {
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("ACCOUNT_NUMBER_DOES_NOT_EXIST");
            errorModel.setMessage("Account Doesn't Exist, can't do withdrawal");
            throw new BusinessException(Collections.singletonList(errorModel));
        }
        AccountEntity accountEntity = optionalAccountEntity.get();
        if (accountEntity.getBalance().compareTo(amount) < 0){
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("INSUFFICIENT_FUND");
            errorModel.setMessage("Insufficient Fund, cant proceed the withdrawal process");
            throw new BusinessException(Collections.singletonList(errorModel));
        }

        BigDecimal newBalance = accountEntity.getBalance().subtract(amount);
        accountEntity.setBalance(newBalance);
        accountRepository.save(accountEntity);

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setAccount(accountEntity);
        transactionEntity.setAmount(amount);
        transactionEntity.setTransactionType(TransactionType.WITHDRAWAL.name());
        transactionEntity.setDate(LocalDate.now());
        transactionRepository.save(transactionEntity);

        return TransactionDTO.fromEntity(transactionEntity);
    }


    @Override
    public TransactionDTO transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        Optional<AccountEntity> optionalFromAccountNumber = accountRepository.findByAccountNumber(fromAccountNumber);
        Optional<AccountEntity> optionalToAccountNumber = accountRepository.findByAccountNumber(toAccountNumber);
        if (optionalToAccountNumber.isEmpty() || optionalFromAccountNumber.isEmpty()) {
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("ONE_OF_THE_ACCOUNT_DOES_NOT_EXIST");
            errorModel.setMessage("One of the Account Number does not exist, please check your input");
            throw new BusinessException(Collections.singletonList(errorModel));
        }
        AccountEntity fromAccountEntity = optionalFromAccountNumber.get();
        if(fromAccountEntity.getBalance().compareTo(amount)<0){
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("INSUFFICIENT_FUND");
            errorModel.setMessage("Insufficient Fund, cant proceed the withdrawal process");
            throw new BusinessException(Collections.singletonList(errorModel));
        }
        AccountEntity toAccountEntity = optionalToAccountNumber.get();

        BigDecimal newBalance = fromAccountEntity.getBalance().subtract(amount);
        fromAccountEntity.setBalance(newBalance);
        accountRepository.save(fromAccountEntity);

        BigDecimal newToAccountBalance = toAccountEntity.getBalance().add(amount);
        toAccountEntity.setBalance(newToAccountBalance);
        accountRepository.save(toAccountEntity);

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setAccount(fromAccountEntity);
        transactionEntity.setAmount(amount);
        transactionEntity.setTransactionType(TransactionType.TRANSFER.name());
        transactionEntity.setDate(LocalDate.now());
        transactionRepository.save(transactionEntity);

        return TransactionDTO.fromEntity(transactionEntity);
    }
}
