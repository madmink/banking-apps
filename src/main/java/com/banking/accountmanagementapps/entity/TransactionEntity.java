package com.banking.accountmanagementapps.entity;

import com.banking.accountmanagementapps.model.TransactionType;
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
    private String transactionType;

    @ManyToOne
    @JoinColumn(name = "account_id" ,nullable = false)
    private AccountEntity account;
}
