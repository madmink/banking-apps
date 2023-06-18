package com.banking.accountmanagementapps.service.impl;

import com.banking.accountmanagementapps.dto.AccountDTO;
import com.banking.accountmanagementapps.entity.AccountEntity;
import com.banking.accountmanagementapps.entity.CustomerEntity;
import com.banking.accountmanagementapps.exception.BusinessException;
import com.banking.accountmanagementapps.repository.AccountRepository;
import com.banking.accountmanagementapps.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @InjectMocks
    AccountServiceImpl accountServiceImpl;

    @Mock
    AccountRepository accountRepository;

    @Mock
    CustomerRepository customerRepository;

    @Test
    void testCreateAccount() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        accountDTO.setAccountNumber("124123");
        accountDTO.setAccountType("CHECKING");
        accountDTO.setBalance(BigDecimal.valueOf(100));
        accountDTO.setCustomerId(1L);

        AccountEntity entity = new AccountEntity();
        entity.setId(1L);
        entity.setAccountNumber("124123");
        entity.setAccountType("CHECKING");
        entity.setBalance(BigDecimal.valueOf(100));

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setIdentityNumber("124123");

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customerEntity));
        when(accountRepository.save(any(AccountEntity.class))).thenReturn(entity);


        AccountDTO result = accountServiceImpl.createAccount(accountDTO);
        assertEquals(accountDTO.getId(),result.getId());

    }

    @Test
    void testCreateAccount_CustomerIdDoesNotExist(){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        accountDTO.setAccountNumber("1124123");
        accountDTO.setAccountType("SAVING");
        accountDTO.setBalance(BigDecimal.valueOf(12241));
        accountDTO.setCustomerId(1L);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class,() ->{
            accountServiceImpl.createAccount(accountDTO);
        });
        assertEquals("CUSTOMER_ID_DOES_NOT_EXIST", exception.getErrors().get(0).getCode());
    }


    @Test
    void testUpdateAccount(){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        accountDTO.setAccountNumber("1124123");
        accountDTO.setAccountType("SAVING");
        accountDTO.setBalance(BigDecimal.valueOf(12241));
        accountDTO.setCustomerId(1L);

        CustomerEntity customerId = new CustomerEntity();
        customerId.setId(1L);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1L);
        accountEntity.setAccountNumber("1124123");
        accountEntity.setAccountType("SAVING");
        accountEntity.setBalance(BigDecimal.valueOf(12241));
        accountEntity.setCustomer(customerId);

        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(accountEntity));
        when(accountRepository.save(any(AccountEntity.class))).thenReturn(accountEntity);

        AccountDTO result = accountServiceImpl.updateAccount(accountDTO, accountDTO.getAccountNumber());
        assertEquals(accountDTO.getAccountNumber(),result.getAccountNumber());
    }

    @Test
    void testUpdateAccount_AccountDoesNotExist(){
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class,() -> {
            accountServiceImpl.updateAccount(new AccountDTO(),"1234");
        });


    }

    @Test
    void testDeleteAccount(){
        AccountEntity account = new AccountEntity();
        account.setAccountNumber("1234");
        account.setBalance(BigDecimal.ZERO);


        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(account));

        assertDoesNotThrow(() ->accountServiceImpl.deleteAccount("1234"));
        verify(accountRepository, times(1)).delete(account);
    }

    @Test
    void testDeleteAccount_AccountNotFound(){
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> accountServiceImpl.deleteAccount("1234"));
        assertEquals("ACCOUNT_NOT_FOUND", exception.getErrors().get(0).getCode());

    }

    @Test
    void testDeleteAccount_AccountHasBalance(){
        AccountEntity account = new AccountEntity();
        account.setId(1L);
        account.setBalance(BigDecimal.valueOf(1000));
        account.setAccountNumber("1234");

        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(account));

        BusinessException exception = assertThrows(BusinessException.class,() -> accountServiceImpl.deleteAccount("1234"));
        assertEquals("ACCOUNT_HAS_BALANCE", exception.getErrors().get(0).getCode());
    }

    @Test
    void testGetAccountByCustomerId(){
        Long customerId = 1L;
        CustomerEntity customer = new CustomerEntity();
        customer.setId(customerId);

        AccountEntity accountSaving = new AccountEntity();
        accountSaving.setId(1L);
        accountSaving.setAccountNumber("123");
        accountSaving.setAccountType("SAVING");
        accountSaving.setBalance(BigDecimal.valueOf(12241));
        accountSaving.setCustomer(customer);

        AccountEntity accountChecking = new AccountEntity();
        accountChecking.setId(2L);
        accountChecking.setAccountNumber("456");
        accountChecking.setAccountType("SAVING");
        accountChecking.setBalance(BigDecimal.valueOf(12241));
        accountChecking.setCustomer(customer);

        List<AccountEntity> accountEntities = Arrays.asList(accountSaving, accountChecking);

        when(accountRepository.findAllByCustomerId(anyLong())).thenReturn(accountEntities);

        List<AccountDTO> accountDTOS = accountServiceImpl.getAccountByCustomerId(customerId);
        assertEquals(2, accountDTOS.size());
        assertEquals("123", accountDTOS.get(0).getAccountNumber());
        assertEquals("456",accountDTOS.get(1).getAccountNumber());
        verify(accountRepository,times(1)).findAllByCustomerId(customerId);

    }
}
