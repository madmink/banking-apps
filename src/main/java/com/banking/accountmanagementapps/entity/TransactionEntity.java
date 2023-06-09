package com.banking.accountmanagementapps.entity;

import com.banking.accountmanagementapps.model.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private Double amount;
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "account_id" ,nullable = false)
    private AccountEntity account;
}
