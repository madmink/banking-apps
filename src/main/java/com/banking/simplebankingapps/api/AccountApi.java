package com.banking.simplebankingapps.api;

import com.banking.simplebankingapps.modules.accountmanagement.service.AccountManagementApplicationService;
import com.banking.simplebankingapps.api.dto.AccountDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountApi {

    private final AccountManagementApplicationService accountService;

    @Autowired
    public AccountApi(AccountManagementApplicationService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody AccountDTO accountDTO){
        AccountDTO createdAccountDTO = accountService.createAccount(accountDTO);
        return new ResponseEntity<>(createdAccountDTO, HttpStatus.CREATED);
    }


    @PutMapping("/update/{accountNumber}")
    public ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountDTO accountDTO, @PathVariable String accountNumber){
        AccountDTO updatedAccountDTO = accountService.updateAccount(accountDTO, accountNumber);
        return new ResponseEntity<>(updatedAccountDTO, HttpStatus.OK);
    }


    @GetMapping("/detail/{customerId}")
    public ResponseEntity<List<AccountDTO>> getAccountByCustomerId(@PathVariable("customerId") Long customerId){
        List<AccountDTO> accountDTOList = accountService.getAccountByCustomerId(customerId);
        return new ResponseEntity<>(accountDTOList,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{accountNumber}")
    public ResponseEntity<String> deleteAccount(@PathVariable("accountNumber") String accountNumber){
        accountService.deleteAccount(accountNumber);
        return new ResponseEntity<>("Account has been deleted successfully", HttpStatus.OK);
    }
}
