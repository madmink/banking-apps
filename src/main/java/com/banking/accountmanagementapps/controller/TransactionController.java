package com.banking.accountmanagementapps.controller;

import com.banking.accountmanagementapps.dto.TransactionDTO;
import com.banking.accountmanagementapps.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/deposit/{accountNumber}")
    public ResponseEntity<TransactionDTO> deposit(@PathVariable String accountNumber, @RequestParam BigDecimal amount){
        TransactionDTO depositTransaction = transactionService.deposit(accountNumber, amount);
        return new ResponseEntity<>(depositTransaction, HttpStatus.CREATED);
    }

    @PostMapping("withdrawn/{accountNumber}")
    public ResponseEntity<TransactionDTO> withdrawn(@PathVariable String accountNumber, @RequestParam BigDecimal amount){
        TransactionDTO withdrawnTransaction = transactionService.withdrawn(accountNumber,amount);
        return new ResponseEntity<>(withdrawnTransaction, HttpStatus.CREATED);
    }

    @PostMapping("transfer/{fromAccountNumber}/{toAccountNumber}")
    public ResponseEntity<TransactionDTO> transfer(@PathVariable String fromAccountNumber, @PathVariable String toAccountNumber, @RequestParam BigDecimal amount){
        TransactionDTO transferTransaction = transactionService.transfer(fromAccountNumber, toAccountNumber, amount);
        return new ResponseEntity<>(transferTransaction, HttpStatus.CREATED);
    }


}
