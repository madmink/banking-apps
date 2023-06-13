package com.banking.accountmanagementapps.controller;

import com.banking.accountmanagementapps.dto.AccountDTO;
import com.banking.accountmanagementapps.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
