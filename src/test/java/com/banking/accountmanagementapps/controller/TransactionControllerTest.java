package com.banking.accountmanagementapps.controller;

import com.banking.accountmanagementapps.dto.TransactionDTO;
import com.banking.accountmanagementapps.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void SetUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testDeposit() throws Exception {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(1L);
        transactionDTO.setAmount(BigDecimal.valueOf(100));
        transactionDTO.setTransactionType("SAVING");

        when(transactionService.deposit(anyString(), any(BigDecimal.class))).thenReturn(transactionDTO);

        mockMvc.perform(post("/api/transaction/deposit/{accountNumber}", "1234").param("amount", "100")
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andExpect(content().json(objectMapper.writeValueAsString(transactionDTO)));
    }

    @Test
    void testWithdrawn() throws Exception {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(1L);
        transactionDTO.setAmount(BigDecimal.valueOf(100));
        transactionDTO.setTransactionType("WITHDRAWN");

        when(transactionService.withdrawn(anyString(), any(BigDecimal.class))).thenReturn(transactionDTO);

        mockMvc.perform(post("/api/transaction/withdrawn/{accountNumber}","1234").param("amount","100").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andExpect(content().json(objectMapper.writeValueAsString(transactionDTO)));

    }

    @Test
    void testTransfer() throws Exception {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(1L);
        transactionDTO.setAmount(BigDecimal.valueOf(100));
        transactionDTO.setTransactionType("TRANSFER");

        when(transactionService.transfer(anyString(),anyString(),any(BigDecimal.class))).thenReturn(transactionDTO);

        mockMvc.perform(post("/api/transaction/transfer/{fromAccountNumber}/{toAccountNumber}", "1234", "4567").param("amount","100").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andExpect(content().json(objectMapper.writeValueAsString(transactionDTO)));

    }
}
