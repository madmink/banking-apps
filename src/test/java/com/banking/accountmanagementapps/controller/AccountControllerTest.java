package com.banking.accountmanagementapps.controller;

import com.banking.accountmanagementapps.dto.AccountDTO;
import com.banking.accountmanagementapps.service.AccountService;
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
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {
    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateAccount() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        accountDTO.setAccountNumber("123456789");
        accountDTO.setAccountType("Saving Account");
        accountDTO.setBalance(BigDecimal.valueOf(10000.0));
        accountDTO.setCustomerId(2L);

        when(accountService.createAccount(any(AccountDTO.class))).thenReturn(accountDTO);

        mockMvc.perform(post("/api/account/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(accountDTO)));
        verify(accountService, times(1)).createAccount(any(AccountDTO.class));
    }

    @Test
    void testGetAccountByCustomerId() throws Exception{
        Long id = 1L;
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(id);
        accountDTO.setAccountNumber("123456789");
        accountDTO.setAccountType("Saving Account");
        accountDTO.setBalance(BigDecimal.valueOf(10000.0));
        accountDTO.setCustomerId(2L);

        when(accountService.getAccountByCustomerId(anyLong())).thenReturn(Collections.singletonList(accountDTO));
        mockMvc.perform(get("/api/account/{customerId}",accountDTO.getCustomerId())).andExpect(status().isOk()).andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(accountDTO))));
    }

    @Test
    void testUpdateAccount() throws Exception{
        String accountNumber = "123124";
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        accountDTO.setCustomerId(2L);
        accountDTO.setAccountType("KTP");


        when(accountService.updateAccount(any(AccountDTO.class), anyString())).thenReturn(accountDTO);
        mockMvc.perform(put("/api/account/update/" + accountNumber).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(accountDTO))).andExpect(status().isOk()).andExpect(content().json(objectMapper.writeValueAsString(accountDTO)));

        verify(accountService, times(1)).updateAccount(any(AccountDTO.class), anyString());



    }

    @Test
    void deleteAccount() throws Exception{
        doNothing().when(accountService).deleteAccount(anyString());

        mockMvc.perform(delete("/api/account/delete/{accountNumber}",1L).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }


}
