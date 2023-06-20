package com.banking.simplebankingapps.api.dto;

import com.banking.simplebankingapps.modules.transactionmanagement.domain.model.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDTO {
    private Long id;
    private LocalDate date;
    private BigDecimal amount;
    private String transactionType;
    private String accountNumber; //assuming each transaction is related to an account

    public static TransactionDTO fromDomain(Transaction transaction){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(transaction.getId());
        transactionDTO.setDate(transaction.getDate());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setTransactionType(transaction.getTransactionType().toString());
        transactionDTO.setAccountNumber(transaction.getAccount().getAccountNumber()); //assuming Transaction has a reference to Account
        return transactionDTO;
    }
}
