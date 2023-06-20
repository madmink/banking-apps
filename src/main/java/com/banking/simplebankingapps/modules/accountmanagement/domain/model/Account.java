package com.banking.simplebankingapps.modules.accountmanagement.domain.model;

import com.banking.simplebankingapps.modules.accountmanagement.exception.AccountManagementException;
import com.banking.simplebankingapps.modules.accountmanagement.infrastructure.entity.AccountEntity;
import com.banking.simplebankingapps.modules.customermanagement.domain.model.Customer;
import com.banking.simplebankingapps.modules.transactionmanagement.domain.model.Transaction;
import com.banking.simplebankingapps.modules.transactionmanagement.infrastructure.entity.TransactionEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class Account {

    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private Customer customer;
    private String accountType;
    private List<Transaction> transactions;

    public Account(Long id, String accountNumber, BigDecimal balance, Customer customer, String accountType , List<Transaction> transactions) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;
        this.customer = customer;
        this.transactions = transactions;
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Deposit amount must be non-negative");
        }
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Withdraw amount must be non-negative");
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new AccountManagementException("Insufficient funds to withdraw");
        }
        this.balance = this.balance.subtract(amount);
    }

    public void transfer(Account toAccount, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Transfer amount must be non-negative");
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new AccountManagementException("Insufficient funds to transfer");
        }
        this.withdraw(amount);
        toAccount.deposit(amount);
    }

    public AccountEntity toAccEntity() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(this.id);
        accountEntity.setAccountNumber(this.accountNumber);
        accountEntity.setBalance(this.balance);
        accountEntity.setCustomer(this.customer.toEntityCustomer());
        if (this.transactions != null) {
            accountEntity.setTransactions(this.transactions.stream().map(Transaction::toEntity).toList());
        }
        return accountEntity;
    }


    public static Account fromAccEntity(AccountEntity accountEntity) {
        List<TransactionEntity> transactionEntities = accountEntity.getTransactions() == null ? Collections.emptyList() : accountEntity.getTransactions();

        return new Account(
                accountEntity.getId(),
                accountEntity.getAccountNumber(),
                accountEntity.getBalance(),
                Customer.fromEntity(accountEntity.getCustomer()),
                accountEntity.getAccountType(),
                transactionEntities.stream().map(Transaction::fromEntity).toList()
        );
    }


}
