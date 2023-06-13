package com.banking.accountmanagementapps.controller;

import com.banking.accountmanagementapps.dto.AccountDTO;
import com.banking.accountmanagementapps.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/account")
public class AccountController {

    private final AccountService accountService;
    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO){
        AccountDTO createdCustomerDTO = accountService.createAccount(accountDTO);
        return new ResponseEntity<>(createdCustomerDTO, HttpStatus.CREATED);
    }

    @GetMapping("getUser/{accountNumber}")
    public ResponseEntity<List<AccountDTO>> getAccountByAccountNumber(@PathVariable("customerId") Long customerId){
        List<AccountDTO> accountDTOList = accountService.getAccountByCustomerId(customerId);
        ResponseEntity<List<AccountDTO>> responseEntity = new ResponseEntity<>(accountDTOList,HttpStatus.OK);
        return responseEntity;
    }

    @PutMapping("update")
    public ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountDTO accountDTO, String accountNumber){
        AccountDTO updateAccountDTO = accountService.updateAccount(accountDTO,accountNumber);
        ResponseEntity<AccountDTO> responseEntity = new ResponseEntity<>(accountDTO, HttpStatus.OK);
        return responseEntity;


    }

    @GetMapping("getaccount")
    public ResponseEntity<List<AccountDTO>> getAccountByCustomerId(@RequestParam("customerId") Long customerId){
        List<AccountDTO> accountDTOList = accountService.getAccountByCustomerId(customerId);
        ResponseEntity<List<AccountDTO>> responseEntity = new ResponseEntity<>(accountDTOList,HttpStatus.OK);
        return responseEntity;
    }



}
