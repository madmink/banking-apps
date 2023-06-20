package com.banking.simplebankingapps.api;

import com.banking.simplebankingapps.api.dto.TransactionDTO;
import com.banking.simplebankingapps.modules.transactionmanagement.service.TransactionManagementApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/transaction")
public class TransactionApi {

    private final TransactionManagementApplicationService transactionManagementApplicationService;

    public TransactionApi(TransactionManagementApplicationService transactionManagementApplicationService){
        this.transactionManagementApplicationService = transactionManagementApplicationService;
    }

    @PostMapping("/deposit/{accountNumber}") //success but id still null,recheck tommorow
    public ResponseEntity<TransactionDTO> deposit(@PathVariable String accountNumber, @RequestParam BigDecimal amount){
        TransactionDTO depositTransaction = transactionManagementApplicationService.deposit(accountNumber, amount);
        return new ResponseEntity<>(depositTransaction, HttpStatus.CREATED);
    }

    @PostMapping("/withdrawn/{accountNumber}")
    public ResponseEntity<TransactionDTO> withdraw(@PathVariable String accountNumber, @RequestParam BigDecimal amount){
        TransactionDTO withdrawnTransaction = transactionManagementApplicationService.withdraw(accountNumber, amount);
        return new ResponseEntity<>(withdrawnTransaction, HttpStatus.CREATED);
    }

    @PostMapping("/transfer/{fromAccountNumber}/{toAccountNumber}")
    public ResponseEntity<TransactionDTO> transfer(@PathVariable String fromAccountNumber, @PathVariable String toAccountNumber, @RequestParam BigDecimal amount){
        TransactionDTO transferTransaction = transactionManagementApplicationService.transfer(fromAccountNumber, toAccountNumber, amount);
        return new ResponseEntity<>(transferTransaction, HttpStatus.CREATED);
    }
}
