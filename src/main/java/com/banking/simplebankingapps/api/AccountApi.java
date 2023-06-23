package com.banking.simplebankingapps.api;

import com.banking.simplebankingapps.api.globalexceptionhandler.CustomErrorType;
import com.banking.simplebankingapps.modules.accountmanagement.exception.AccountManagementException;
import com.banking.simplebankingapps.modules.accountmanagement.service.AccountManagementService;
import com.banking.simplebankingapps.modules.accountmanagement.dto.AccountDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountApi {

    private final AccountManagementService accountService;

    @Autowired
    public AccountApi(AccountManagementService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
        try {
            AccountDTO createdAccountDTO = accountService.createAccount(accountDTO);
            return ResponseEntity.ok(createdAccountDTO);
        } catch (AccountManagementException e) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add(e.getMessage());
            return new ResponseEntity<>(new CustomErrorType(e.getErrorCode(), errorMessages), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{accountNumber}")
    public ResponseEntity<Object> updateAccount(@PathVariable String accountNumber, @RequestBody AccountDTO accountDTO) {
        try {
            AccountDTO updatedAccountDTO = accountService.updateAccount(accountDTO, accountNumber);
            return ResponseEntity.ok(updatedAccountDTO);
        } catch (AccountManagementException e) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add(e.getMessage());
            return new ResponseEntity<>(new CustomErrorType(e.getErrorCode(), errorMessages), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/detail/{customerId}")
    public ResponseEntity<Object> getAccountByCustomerId(@PathVariable("customerId") Long customerId) {
        try {
            List<AccountDTO> accountDTOList = accountService.getAccountByCustomerId(customerId);
            return ResponseEntity.ok(accountDTOList);
        } catch (AccountManagementException e) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add(e.getMessage());
            return new ResponseEntity<>(new CustomErrorType(e.getErrorCode(), errorMessages), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/delete/{accountNumber}")
    public ResponseEntity<String> deleteAccount(@PathVariable("accountNumber") String accountNumber){
        accountService.deleteAccount(accountNumber);
        return new ResponseEntity<>("Account has been deleted successfully", HttpStatus.OK);
    }
}
