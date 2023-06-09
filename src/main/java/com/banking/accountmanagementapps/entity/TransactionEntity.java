package com.banking.accountmanagementapps.entity;

import com.banking.accountmanagementapps.model.TransactionType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Table
@Getter
@Setter
@NoArgsConstructor
public class TransactionEntity {
    private Long id;
    private LocalDate date;
    private Double amount;
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "account_id" ,nullable = false)
    private AccountEntity account;
}
