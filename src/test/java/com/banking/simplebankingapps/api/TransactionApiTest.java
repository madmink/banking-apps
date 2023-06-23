package com.banking.simplebankingapps.api;

import com.banking.simplebankingapps.api.dto.TransactionDTO;
import com.banking.simplebankingapps.modules.transactionmanagement.domain.model.Transaction;
import com.banking.simplebankingapps.modules.transactionmanagement.service.TransactionManagementApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
public class TransactionApiTest {

    @InjectMocks
    private TransactionManagementApplicationService transactionManagementApplicationService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void testDepositSuccessful() throws Exception {
        // Prepare Mock Data
        TransactionDTO depositTransaction = new TransactionDTO();
        depositTransaction.setId(1L);
        depositTransaction.setAccountNumber("123456789");
        depositTransaction.setAmount(BigDecimal.valueOf(100.0));
        depositTransaction.setType(TransactionType.DEPOSIT);

        // Mock Service Method
        when(transactionManagementApplicationService.deposit(any(String.class), any(BigDecimal.class))).thenReturn(depositTransaction);

        // Perform Request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/transaction/deposit/{accountNumber}", "123456789")
                        .param("amount", "100.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value("123456789"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(100.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("DEPOSIT"));
    }

    @Test
    void testDepositInvalidAccountNumber() throws Exception {
        // Mock Service Method to throw exception
        when(transactionManagementApplicationService.deposit(any(String.class), any(BigDecimal.class)))
                .thenThrow(new TransactionManagementException("INVALID_ACCOUNT_NUMBER", "Invalid account number"));

        // Perform Request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/transaction/deposit/{accountNumber}", "invalid")
                        .param("amount", "100.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("INVALID_ACCOUNT_NUMBER"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages[0]").value("Invalid account number"));
    }
}
