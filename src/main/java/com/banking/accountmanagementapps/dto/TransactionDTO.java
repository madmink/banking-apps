package com.banking.accountmanagementapps.dto;

import com.banking.accountmanagementapps.model.TransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDTO {
    private Long id;
    private LocalDate date;
    private Double amount;
    private TransactionType transactionType;
}
