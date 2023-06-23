package com.banking.simplebankingapps.modules.transactionmanagement.service;

import com.banking.simplebankingapps.api.dto.TransactionDTO;
import com.banking.simplebankingapps.modules.accountmanagement.domain.model.Account;
import com.banking.simplebankingapps.modules.accountmanagement.domain.repository.AccountRepository;
import com.banking.simplebankingapps.modules.accountmanagement.exception.AccountManagementException;
import com.banking.simplebankingapps.modules.accountmanagement.infrastructure.entity.AccountEntity;
import com.banking.simplebankingapps.modules.transactionmanagement.domain.model.Transaction;
import com.banking.simplebankingapps.modules.transactionmanagement.domain.repository.TransactionRepository;
import com.banking.simplebankingapps.modules.transactionmanagement.infrastructure.entity.TransactionEntity;
import com.banking.simplebankingapps.shared.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TransactionManagementApplicationService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public TransactionDTO deposit(String accountNumber, BigDecimal amount) {
        AccountEntity accountEntity = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountManagementException("DEPOSIT_ERROR_ACCOUNT_NOT_FOUND", "Account Doesn't Exist, please create a new account to deposit"));

        Account account = Account.fromAccountEntity(accountEntity);
        account.deposit(amount);

        // Update the balance in the account entity
        accountEntity.setBalance(account.getBalance());
        accountRepository.save(accountEntity);

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setDate(LocalDate.now());
        transactionEntity.setAmount(amount);
        transactionEntity.setTransactionType(TransactionType.DEPOSIT);
        transactionEntity.setAccount(accountEntity);

        transactionEntity = transactionRepository.save(transactionEntity);

        Transaction transaction = new Transaction(
                transactionEntity.getId(), // Use the actual transaction ID from transactionEntity
                transactionEntity.getDate(),
                transactionEntity.getAmount(),
                transactionEntity.getTransactionType().toString(),
                account
        );

        return TransactionDTO.fromDomain(transaction);
    }



    @Transactional
    public TransactionDTO withdraw(String accountNumber, BigDecimal amount) {
        AccountEntity accountEntity = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountManagementException("WITHDRAW_ERROR", "Account Doesn't Exist, can't do withdrawal"));

        Account account = Account.fromAccountEntity(accountEntity);

        if (account.getBalance().compareTo(amount) < 0) {
            throw new AccountManagementException("WITHDRAW_ERROR", "Insufficient Funds, can't proceed with the withdrawal process");
        }

        account.withdraw(amount);
        accountEntity = accountRepository.save(accountEntity);

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setDate(LocalDate.now());
        transactionEntity.setAmount(amount);
        transactionEntity.setTransactionType(TransactionType.valueOf(TransactionType.WITHDRAWAL.name())); // Convert enum to string
        transactionEntity.setAccount(accountEntity);

        transactionEntity = transactionRepository.save(transactionEntity);

        Transaction transaction = new Transaction(
                transactionEntity.getId(),
                transactionEntity.getDate(),
                transactionEntity.getAmount(),
                String.valueOf((transactionEntity.getTransactionType())), // Convert string to enum
                account
        );

        return TransactionDTO.fromDomain(transaction);
    }



    @Transactional
    public TransactionDTO transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        AccountEntity fromAccountEntity = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new AccountManagementException("TRANSFER_ERROR_SENDER_NOT_FOUND", "Account with number " + fromAccountNumber + " does not exist"));
        AccountEntity toAccountEntity = accountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new AccountManagementException("TRANSFER_ERROR_RECEIVER_NOT_FOUND", "Account with number " + toAccountNumber + " does not exist"));

        Account fromAccount = Account.fromAccountEntity(fromAccountEntity);
        Account toAccount = Account.fromAccountEntity(toAccountEntity);

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new AccountManagementException("TRANSFER_ERROR_INSUFFICIENT_FUND", "Insufficient Funds, can't proceed with the transfer process");
        }

        fromAccount.transfer(toAccount, amount);

        fromAccountEntity = accountRepository.save(fromAccountEntity);
        toAccountEntity = accountRepository.save(toAccountEntity);

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setDate(LocalDate.now());
        transactionEntity.setAmount(amount);
        transactionEntity.setTransactionType(TransactionType.valueOf(TransactionType.TRANSFER.name())); // Convert enum to string
        transactionEntity.setAccount(fromAccountEntity);

        transactionEntity = transactionRepository.save(transactionEntity);

        Transaction transaction = new Transaction(
                transactionEntity.getId(),
                transactionEntity.getDate(),
                transactionEntity.getAmount(),
                String.valueOf((String.valueOf(transactionEntity.getTransactionType()))), // Convert string to enum
                fromAccount
        );

        return TransactionDTO.fromDomain(transaction);
    }


}
