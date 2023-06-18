package com.banking.accountmanagementapps.service.impl;

import com.banking.accountmanagementapps.dto.TransactionDTO;
import com.banking.accountmanagementapps.entity.AccountEntity;
import com.banking.accountmanagementapps.entity.TransactionEntity;
import com.banking.accountmanagementapps.exception.BusinessException;
import com.banking.accountmanagementapps.model.TransactionType;
import com.banking.accountmanagementapps.repository.AccountRepository;
import com.banking.accountmanagementapps.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {
    @InjectMocks
    TransactionServiceImpl transactionServiceImpl;

    @Mock
    AccountRepository accountRepository;

    @Mock
    TransactionRepository transactionRepository;

    private String accountNumber;
    private String toAccountNumber;
    private BigDecimal initialBalance;
    private AccountEntity accountEntity;
    private TransactionEntity transactionEntity;

    @BeforeEach
    void SetUp() {
        accountNumber = "123456789";
        toAccountNumber = "bcdedit";
        accountEntity = new AccountEntity();
        accountEntity.setAccountNumber(accountNumber);
        accountEntity.setBalance(BigDecimal.valueOf(100));
        accountEntity.setId(1L);

        transactionEntity = new TransactionEntity();
        transactionEntity.setAccount(accountEntity);
        transactionEntity.setAmount(initialBalance);
    }

    @Nested
    class AllTestDeposit {
        @Test
        void testDeposit() {
            BigDecimal depositAmount = BigDecimal.valueOf(10000);
            BigDecimal expectedBalance = accountEntity.getBalance().add(depositAmount);

            TransactionEntity transaction = new TransactionEntity();
            transaction.setAccount(accountEntity);
            transaction.setAmount(depositAmount);
            transaction.setTransactionType(TransactionType.DEPOSIT.name());

            when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(accountEntity));
            when(accountRepository.save(any(AccountEntity.class))).thenAnswer(i -> {
                AccountEntity savedAccount = (AccountEntity) i.getArguments()[0];
                assertEquals(expectedBalance, savedAccount.getBalance());
                return savedAccount;
            });
            when(transactionRepository.save(any(TransactionEntity.class))).thenAnswer(i -> i.getArguments()[0]);

            TransactionDTO transactionDTO = transactionServiceImpl.deposit(accountNumber, depositAmount);

            assertEquals(depositAmount, transactionDTO.getAmount());
            verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
            verify(accountRepository, times(1)).save(any(AccountEntity.class));
            verify(transactionRepository, times(1)).save(any(TransactionEntity.class));
        }

        @Test
        void testDeposit_AccountNumberDoesNotExist() {
            when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.empty());

            BusinessException exception = assertThrows(BusinessException.class, () ->
                    transactionServiceImpl.deposit("1234", BigDecimal.valueOf(100))
            );

            assertEquals("ACCOUNT_NUMBER_DOES_NOT_EXIST", exception.getErrors().get(0).getCode());
        }

    @Nested
    class AllTestWithdrawn {


        @Test
        void testWithdrawn() {
            BigDecimal withdrawnAmount = BigDecimal.valueOf(1);
            BigDecimal expectedAmount = accountEntity.getBalance().subtract(withdrawnAmount);
            String accountNumber = "1234";

            when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(accountEntity));

            ArgumentCaptor<AccountEntity> accountCaptor = ArgumentCaptor.forClass(AccountEntity.class);
            when(accountRepository.save(accountCaptor.capture())).thenReturn(null);

            when(transactionRepository.save(any(TransactionEntity.class))).thenAnswer(i -> i.getArguments()[0]);

            TransactionDTO result = transactionServiceImpl.withdrawn(accountNumber, withdrawnAmount);

            AccountEntity savedAccount = accountCaptor.getValue();
            assertEquals(expectedAmount, savedAccount.getBalance());

            verify(accountRepository).findByAccountNumber(accountNumber);
            verify(accountRepository).save(any(AccountEntity.class));
            verify(transactionRepository).save(any(TransactionEntity.class));
        }


        @Test
        void testWithdrawn_AccountNumberDoesNotExist() {
            when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.empty());

            BusinessException exception = assertThrows(BusinessException.class, () ->
                    transactionServiceImpl.withdrawn("1234", BigDecimal.valueOf(100))
            );

            assertEquals("ACCOUNT_NUMBER_DOES_NOT_EXIST", exception.getErrors().get(0).getCode());
        }


        @Test
        void testWithdrawn_InsufficientFund() {
            BigDecimal amountWithdrawn = BigDecimal.valueOf(1000);
            String accountNumber = "1234";

            when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(accountEntity));

            assertThrows(BusinessException.class, () -> transactionServiceImpl.withdrawn(accountNumber, amountWithdrawn));

        }
    }

    @Nested
    class AllTestTransfer {


        @Test
        void testTransfer() {
            String fromAccountNumber = "12345";
            String toAccountNumber = "67890";
            BigDecimal transferAmount = BigDecimal.valueOf(100);

            AccountEntity fromAccountEntity = new AccountEntity();
            fromAccountEntity.setAccountNumber(fromAccountNumber);
            fromAccountEntity.setBalance(BigDecimal.valueOf(500));

            AccountEntity toAccountEntity = new AccountEntity();
            toAccountEntity.setAccountNumber(toAccountNumber);
            toAccountEntity.setBalance(BigDecimal.valueOf(300));

            when(accountRepository.findByAccountNumber(fromAccountNumber)).thenReturn(Optional.of(fromAccountEntity));
            when(accountRepository.findByAccountNumber(toAccountNumber)).thenReturn(Optional.of(toAccountEntity));

            when(accountRepository.save(any(AccountEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
            when(transactionRepository.save(any(TransactionEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

            TransactionDTO result = transactionServiceImpl.transfer(fromAccountNumber, toAccountNumber, transferAmount);

            assertEquals(BigDecimal.valueOf(400), fromAccountEntity.getBalance());
            assertEquals(BigDecimal.valueOf(400), toAccountEntity.getBalance());

            assertEquals(transferAmount, result.getAmount());
            assertEquals(TransactionType.TRANSFER.name(), result.getTransactionType());

            verify(accountRepository).findByAccountNumber(fromAccountNumber);
            verify(accountRepository).findByAccountNumber(toAccountNumber);
            verify(accountRepository, times(2)).save(any(AccountEntity.class));
            verify(transactionRepository).save(any(TransactionEntity.class));
        }


        @Test
        void testTransferWhenOneAccountDoesNotExist() {
            BigDecimal transferAmount = BigDecimal.valueOf(100);

            when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

            BusinessException exception = assertThrows(
                    BusinessException.class,
                    () -> transactionServiceImpl.transfer(accountNumber, toAccountNumber, transferAmount)
            );

            assertEquals("ONE_OF_THE_ACCOUNT_DOES_NOT_EXIST", exception.getErrors().get(0).getCode());
            assertEquals("One of the Account Number does not exist, please check your input", exception.getErrors().get(0).getMessage());

            verify(accountRepository).findByAccountNumber(accountNumber);
        }

        @Test
        void testTransferWhenInsufficientFunds() {
            BigDecimal transferAmount = BigDecimal.valueOf(1000);

            AccountEntity fromAccountEntity = new AccountEntity();
            fromAccountEntity.setAccountNumber(accountNumber);
            fromAccountEntity.setBalance(BigDecimal.valueOf(500));

            AccountEntity toAccountEntity = new AccountEntity();
            toAccountEntity.setAccountNumber(toAccountNumber);
            toAccountEntity.setBalance(BigDecimal.valueOf(300));

            when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(fromAccountEntity));
            when(accountRepository.findByAccountNumber(toAccountNumber)).thenReturn(Optional.of(toAccountEntity));

            BusinessException exception = assertThrows(
                    BusinessException.class,
                    () -> transactionServiceImpl.transfer(accountNumber, toAccountNumber, transferAmount)
            );

            assertEquals("INSUFFICIENT_FUND", exception.getErrors().get(0).getCode());
            assertEquals("Insufficient Fund, cant proceed the withdrawal process", exception.getErrors().get(0).getMessage());

            verify(accountRepository).findByAccountNumber(accountNumber);
            verify(accountRepository).findByAccountNumber(toAccountNumber);
        }
    }

}}
