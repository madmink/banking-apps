package com.banking.simplebankingapps.api;

import com.banking.simplebankingapps.modules.accountmanagement.dto.AccountDTO;
import com.banking.simplebankingapps.modules.accountmanagement.exception.AccountManagementException;
import com.banking.simplebankingapps.modules.accountmanagement.service.AccountManagementService;
import com.banking.simplebankingapps.modules.customermanagement.domain.repository.CustomerRepository;
import com.banking.simplebankingapps.shared.AccountType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AccountApiTest {
    @InjectMocks
    private AccountApi accountApi;

    @Mock
    private AccountManagementService accountManagementApplicationService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private AccountDTO accountDTO;

    @BeforeEach
    void SetUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountApi).build();
        objectMapper = new ObjectMapper();
        accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        accountDTO.setAccountNumber("1241231413");
        accountDTO.setBalance(BigDecimal.valueOf(141232));
        accountDTO.setCustomerId(2L);
        accountDTO.setAccountType(AccountType.SAVINGS);
    }

    @Nested
    class AllTestCreateAccount {

        @Test
        void testCreateAccountSuccessful() throws Exception {
            when(accountManagementApplicationService.createAccount(any(AccountDTO.class))).thenReturn(accountDTO);

            mockMvc.perform(post("/api/account/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(accountDTO)))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(accountDTO)));
        }

        @Test
        void testCreateAccountFailed_CustomerDoesNotExist() throws Exception {
            when(accountManagementApplicationService.createAccount(any(AccountDTO.class))).thenThrow(new AccountManagementException("ACCOUNT_ERROR_CUSTOMER_DOES_NOT_EXIST", "Customer Does Not Exist"));

            AccountDTO accountDTO = new AccountDTO();

            mockMvc.perform(post("/api/account/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(accountDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json("{\"errorCode\":\"ACCOUNT_ERROR_CUSTOMER_DOES_NOT_EXIST\",\"errorMessage\":[\"Customer Does Not Exist\"]}"));


        }

    }

    @Nested
    class AllTestUpdateAccount {

        @Test
        void testUpdateSuccessful() throws Exception {

            AccountDTO createdAccountDTO = new AccountDTO();

            when(accountManagementApplicationService.createAccount(any(AccountDTO.class))).thenReturn(createdAccountDTO);

            AccountDTO accountDTO = new AccountDTO();

            mockMvc.perform(post("/api/account/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(accountDTO)))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(createdAccountDTO)));

        }

        @Test
        void testUpdateFailure_AccountNumberNotFound() throws Exception {

            AccountDTO accountDTO = new AccountDTO();

            String errorCode = "YOUR_BELOVED_ERROR_CODE";
            String errorMessage = "Account Number could not be found.";

            when(accountManagementApplicationService.updateAccount(any(AccountDTO.class), anyString()))
                    .thenThrow(new AccountManagementException(errorCode, errorMessage));

            mockMvc.perform(put("/api/account/update/{accountNumber}", 1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(accountDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errorCode", Matchers.is(errorCode)))
                    .andExpect(jsonPath("$.errorMessage[0]", Matchers.is(errorMessage)));

        }

    }

    @Nested
    class AllTestGetCustomer{
        @Test
        void testGetCustomerSuccessful () throws Exception{
            // Prepare test data
            Long customerId = 1L;
            List<AccountDTO> accountDTOList = new ArrayList<>();
            AccountDTO accountDTO1 = new AccountDTO();
            accountDTO1.setId(1L);
            accountDTO1.setAccountNumber("123456789");
            accountDTO1.setBalance(BigDecimal.valueOf(1000));
            accountDTOList.add(accountDTO1);
            when(accountManagementApplicationService.getAccountByCustomerId(customerId)).thenReturn(accountDTOList);

            // Perform the GET request
            mockMvc.perform(get("/api/account/detail/{customerId}", customerId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(accountDTOList)));

            // Verify the service method was called
            verify(accountManagementApplicationService).getAccountByCustomerId(customerId);
        }

        @Test
        void testGetAccountByCustomerId_ThrowsException() throws Exception {
            // Prepare test data
            Long customerId = 1L;
            String errorCode = "CUSTOMER_NOT_FOUND";
            String errorMessage = "Customer not found";
            when(accountManagementApplicationService.getAccountByCustomerId(customerId))
                    .thenThrow(new AccountManagementException(errorCode, errorMessage));

            // Perform the GET request
            mockMvc.perform(get("/api/account/detail/{customerId}", customerId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errorCode").value(errorCode))
                    .andExpect(jsonPath("$.errorMessage[0]").value(errorMessage));

            // Verify the service method was called
            verify(accountManagementApplicationService).getAccountByCustomerId(customerId);
        }

        @Test
        void testDeleteAccount() throws Exception{

            doNothing().when(accountManagementApplicationService).deleteAccount(anyString());

            mockMvc.perform(delete("/api/account/delete/{accountNumber",1L)
                    .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        }
        }
    }

