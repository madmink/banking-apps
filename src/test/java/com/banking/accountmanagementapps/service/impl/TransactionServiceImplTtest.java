package com.banking.accountmanagementapps.service.impl;

import com.banking.accountmanagementapps.dto.TransactionDTO;
import com.banking.accountmanagementapps.entity.AccountEntity;
import com.banking.accountmanagementapps.entity.TransactionEntity;
import com.banking.accountmanagementapps.exception.BusinessException;
import com.banking.accountmanagementapps.model.TransactionType;
import com.banking.accountmanagementapps.repository.AccountRepository;
import com.banking.accountmanagementapps.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTtest {
    @InjectMocks
    TransactionServiceImpl transactionServiceImpl;

    @Mock
    AccountRepository accountRepository;

    @Mock
    TransactionRepository transactionRepository;

    @BeforeEach
    void SetUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeposit() {
        String accountNumber = "1234";
        BigDecimal initialBalance = BigDecimal.valueOf(100);
        BigDecimal depositAmount = BigDecimal.valueOf(10000);
        BigDecimal expectedBalance = initialBalance.add(depositAmount);

        AccountEntity account = new AccountEntity();
        account.setId(1L);
        account.setAccountNumber("1234");
        account.setAccountType("SAVING");
        account.setBalance(BigDecimal.valueOf(100));

        TransactionEntity transaction = new TransactionEntity();
        transaction.setAccount(account);
        transaction.setAmount(depositAmount);
        transaction.setTransactionType(TransactionType.DEPOSIT.name());

        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(account));
        when(accountRepository.save(any(AccountEntity.class))).thenAnswer(i -> {
            AccountEntity savedAccount = (AccountEntity) i.getArguments()[0];
            assertEquals(expectedBalance, savedAccount.getBalance());  // Assert that the account balance has been updated correctly
            return savedAccount;
        });
        when(transactionRepository.save(any(TransactionEntity.class))).thenAnswer(i -> i.getArguments()[0]);  // Return what was passed

        TransactionDTO transactionDTO = transactionServiceImpl.deposit(accountNumber, depositAmount);

        assertEquals(depositAmount, transactionDTO.getAmount());
        assertEquals(TransactionType.DEPOSIT.name(), transactionDTO.getTransactionType());

        verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
        verify(accountRepository, times(1)).save(any(AccountEntity.class));
        verify(transactionRepository, times(1)).save(any(TransactionEntity.class));
    }

    @Test
    void testDeposit_AccountNumberDoesNotExist() {
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> transactionServiceImpl.deposit("1234", BigDecimal.valueOf(100)));
        assertEquals("ACCOUNT_NUMBER_DOES_NOT_EXIST", exception.getErrors().get(0).getCode());

    }

    @Test
    void testWithdrawn() {
        BigDecimal initialAmount = BigDecimal.valueOf(1000);
        BigDecimal withdrawnAmount = BigDecimal.valueOf(100);
        BigDecimal expectedAmount = initialAmount.subtract(withdrawnAmount);
        String accountNumber = "1234";

        AccountEntity entity = new AccountEntity();
        entity.setBalance(BigDecimal.valueOf(1000));
        entity.setId(1L);

        TransactionEntity transaction = new TransactionEntity();
        transaction.setAmount(withdrawnAmount);

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(entity));
        when(accountRepository.save(any((AccountEntity.class)))).thenAnswer(i -> {
            AccountEntity savedAccount = (AccountEntity) i.getArguments()[0];
            assertEquals(expectedAmount, savedAccount.getBalance());
            return savedAccount;
        });
        when(transactionRepository.save(any(TransactionEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        TransactionDTO result = transactionServiceImpl.withdrawn(accountNumber, withdrawnAmount);

        assertEquals(withdrawnAmount, transaction.getAmount());

        verify(accountRepository,times(1)).findByAccountNumber(accountNumber);
        verify(accountRepository,times(1)).save(any(AccountEntity.class));
        verify(transactionRepository, times(1)).save(any(TransactionEntity.class));

    }

    @Test
    void testWithdrawn_AccountNumberDoesNotExist() {
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> transactionServiceImpl.withdrawn("1234",BigDecimal.valueOf(100)));
        assertEquals("ACCOUNT_NUMBER_DOES_NOT_EXIST",exception.getErrors().get(0).getCode());

    }

    @Test
    void testWithdrawn_InsufficientFund() {

    }

    @Test
    void testTransfer() {

    }

    @Test
    void testTransfer_OneOfAccountDoesNotExist() {
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.empty());

    }

    @Test
    void testTransfer_InsufficientFund() {

    }


}
