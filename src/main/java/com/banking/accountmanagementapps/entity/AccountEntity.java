package com.banking.accountmanagementapps.entity;

import com.banking.accountmanagementapps.model.AccountType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private Double balance;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private CustomerEntity customer;

    @OneToMany(mappedBy = "account")
    private List<TransactionEntity> transactions;

}
