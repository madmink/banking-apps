package com.banking.accountmanagementapps.dto;

import com.banking.accountmanagementapps.entity.TransactionEntity;
import com.banking.accountmanagementapps.model.TransactionType;
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

    public TransactionEntity toEntity(){
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(this.id);
        transactionEntity.setDate(this.date);
        transactionEntity.setAmount(this.amount);
        transactionEntity.setTransactionType(this.transactionType);
        return transactionEntity;
    }

    public static TransactionDTO fromEntity(TransactionEntity transactionEntity){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(transactionEntity.getId());
        transactionDTO.setDate(transactionEntity.getDate());
        transactionDTO.setAmount(transactionEntity.getAmount());
        transactionDTO.setTransactionType(transactionEntity.getTransactionType());
        return transactionDTO;

    }


}
