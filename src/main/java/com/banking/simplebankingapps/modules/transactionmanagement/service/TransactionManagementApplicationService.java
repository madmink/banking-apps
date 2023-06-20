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
        Account account = Account.fromAccEntity(accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountManagementException("Account Doesn't Exist, please create new account to deposit")));

        account.deposit(amount);
        AccountEntity accountEntity = account.toAccEntity();
        accountRepository.save(accountEntity);

        TransactionEntity transactionEntity = new TransactionEntity(
                LocalDate.now(),
                amount,
                TransactionType.DEPOSIT.toString(),
                accountEntity
        );

        transactionEntity = transactionRepository.save(transactionEntity);

        Transaction transaction = new Transaction(transactionEntity.getId(), transactionEntity.getDate(), transactionEntity.getAmount(), transactionEntity.getTransactionType(), account);

        return TransactionDTO.fromDomain(transaction);
    }


    @Transactional
    public TransactionDTO withdraw(String accountNumber, BigDecimal amount) {
        Account account = Account.fromAccEntity(accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountManagementException("Account Doesn't Exist, can't do withdrawal")));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new AccountManagementException("Insufficient Fund, can't proceed the withdrawal process");
        }

        account.withdraw(amount);
        AccountEntity accountEntity = account.toAccEntity();
        accountRepository.save(accountEntity);

        TransactionEntity transactionEntity = new TransactionEntity(
                LocalDate.now(),
                amount,
                TransactionType.WITHDRAWAL.toString(),
                accountEntity
        );

        transactionEntity = transactionRepository.save(transactionEntity);

        Transaction transaction = new Transaction(transactionEntity.getId(), transactionEntity.getDate(), transactionEntity.getAmount(), transactionEntity.getTransactionType(), account);

        return TransactionDTO.fromDomain(transaction);
    }


    @Transactional
    public TransactionDTO transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        Account fromAccount = Account.fromAccEntity(accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new AccountManagementException("Account with number " + fromAccountNumber + " does not exist")));
        Account toAccount = Account.fromAccEntity(accountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new AccountManagementException("Account with number " + toAccountNumber + " does not exist")));

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new AccountManagementException("Insufficient Fund, can't proceed the transfer process");
        }

        fromAccount.transfer(toAccount, amount);
        AccountEntity fromAccountEntity = fromAccount.toAccEntity();
        AccountEntity toAccountEntity = toAccount.toAccEntity();
        accountRepository.save(fromAccountEntity);
        accountRepository.save(toAccountEntity);

        TransactionEntity transactionEntity = new TransactionEntity(
                LocalDate.now(),
                amount,
                TransactionType.TRANSFER.toString(),
                fromAccountEntity
        );

        transactionEntity = transactionRepository.save(transactionEntity);

        Transaction transaction = new Transaction(transactionEntity.getId(), transactionEntity.getDate(), transactionEntity.getAmount(), transactionEntity.getTransactionType(), fromAccount);

        return TransactionDTO.fromDomain(transaction);
    }

}
