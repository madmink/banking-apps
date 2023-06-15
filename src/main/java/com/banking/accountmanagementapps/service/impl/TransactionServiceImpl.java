package com.banking.accountmanagementapps.service.impl;

import com.banking.accountmanagementapps.dto.TransactionDTO;
import com.banking.accountmanagementapps.entity.AccountEntity;

import com.banking.accountmanagementapps.entity.TransactionEntity;
import com.banking.accountmanagementapps.model.TransactionType;
import com.banking.accountmanagementapps.repository.AccountRepository;
import com.banking.accountmanagementapps.repository.TransactionRepository;
import com.banking.accountmanagementapps.service.AccountService;
import com.banking.accountmanagementapps.service.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;


@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public TransactionDTO deposit(String accountNumber, BigDecimal amount) {
        Optional<AccountEntity> optionalAccountEntity = accountRepository.findByAccountNumber(accountNumber);
        if(!optionalAccountEntity.isPresent()){
            //later insert logic for exception here

        }
        AccountEntity accountEntity = optionalAccountEntity.get();
        BigDecimal newBalance =accountEntity.getBalance().add(amount);
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
            if(!optionalAccountEntity.isPresent()){
                //later insert logic for exception here
            }
            AccountEntity accountEntity = optionalAccountEntity.get();
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
        if(!optionalToAccountNumber.isPresent() || !optionalFromAccountNumber.isPresent()){
            // insert exception here when one of the Account Number is not avaliable
        }
        AccountEntity fromAccountEntity = optionalFromAccountNumber.get();
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
