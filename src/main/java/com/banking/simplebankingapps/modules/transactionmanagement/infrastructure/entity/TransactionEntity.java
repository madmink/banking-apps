package com.banking.simplebankingapps.modules.transactionmanagement.infrastructure.entity;

import com.banking.simplebankingapps.modules.accountmanagement.infrastructure.entity.AccountEntity;
import com.banking.simplebankingapps.shared.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "TRANSACTION_TABLE")
@Getter
@Setter
@NoArgsConstructor
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)  // Add this annotation to map the enum as a string
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    public TransactionEntity(LocalDate date, BigDecimal amount, TransactionType transactionType, AccountEntity account) {
        this.date = date;
        this.amount = amount;
        this.transactionType = transactionType;
        this.account = account;
    }
}
