package com.banking.simplebankingapps.api;

import com.banking.simplebankingapps.modules.transactionmanagement.dto.TransactionDTO;
import com.banking.simplebankingapps.api.globalexceptionhandler.CustomErrorType;
import com.banking.simplebankingapps.modules.transactionmanagement.exception.TransactionManagementException;
import com.banking.simplebankingapps.modules.transactionmanagement.service.TransactionManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/transaction")
public class TransactionApi {

    private final TransactionManagementService transactionManagementApplicationService;

    public TransactionApi(TransactionManagementService transactionManagementApplicationService){
        this.transactionManagementApplicationService = transactionManagementApplicationService;
    }

    @PostMapping("/deposit/{accountNumber}")
    public ResponseEntity<Object> deposit(@PathVariable String accountNumber, @RequestParam BigDecimal amount) {
        try {
            TransactionDTO depositTransaction = transactionManagementApplicationService.deposit(accountNumber, amount);
            depositTransaction.setId(1L); // Set the id based on the actual transaction id
            return ResponseEntity.status(HttpStatus.CREATED).body(depositTransaction);
        } catch (TransactionManagementException e) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add(e.getMessage());
            return new ResponseEntity<>(new CustomErrorType(e.getErrorCode(), errorMessages), HttpStatus.BAD_REQUEST);
        }
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
