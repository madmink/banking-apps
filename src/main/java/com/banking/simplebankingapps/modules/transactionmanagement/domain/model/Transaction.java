package com.banking.simplebankingapps.modules.transactionmanagement.domain.model;

import com.banking.simplebankingapps.modules.accountmanagement.domain.model.Account;
import com.banking.simplebankingapps.modules.transactionmanagement.infrastructure.entity.TransactionEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Transaction {

    private Long id;
    private LocalDate date;
    private BigDecimal amount;
    private String transactionType;
    private Account account;

    public Transaction(Long id, LocalDate date, BigDecimal amount, String transactionType, Account account) {
        this.date = date;
        this.amount = amount;
        this.transactionType = transactionType;
        this.account = account;
    }
    public TransactionEntity toEntity() {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(this.id);
        transactionEntity.setDate(this.date);
        transactionEntity.setAmount(this.amount);
        transactionEntity.setTransactionType(this.transactionType);
        transactionEntity.setAccount(this.account.toAccEntity());
        return transactionEntity;
    }

    public static Transaction fromEntity(TransactionEntity transactionEntity) {
        return new Transaction(
                transactionEntity.getId(),
                transactionEntity.getDate(),
                transactionEntity.getAmount(),
                transactionEntity.getTransactionType(),
                Account.fromAccEntity(transactionEntity.getAccount())  // Assuming you have a fromEntity method in Account
        );
    }



    // If there are any methods relating to the business rules and logic specific to a transaction, they can be placed here.
}
