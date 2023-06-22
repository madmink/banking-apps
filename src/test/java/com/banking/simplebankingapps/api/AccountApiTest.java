package com.banking.simplebankingapps.api;

import com.banking.simplebankingapps.api.dto.AccountDTO;
import com.banking.simplebankingapps.modules.accountmanagement.infrastructure.entity.AccountEntity;
import com.banking.simplebankingapps.modules.accountmanagement.service.AccountManagementApplicationService;
import com.banking.simplebankingapps.shared.AccountType;
import com.banking.simplebankingapps.shared.IdentityCardType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class AccountApiTest {
    @InjectMocks
    private AccountApi accountApi;

    @Mock
    private AccountManagementApplicationService accountManagementApplicationService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private AccountDTO accountDTO;

    @BeforeEach
    void SetUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(accountApi).build();
        objectMapper = new ObjectMapper();
        accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        accountDTO.setAccountNumber("1241231413");
        accountDTO.setAccountType(AccountType.SAVINGS);
        accountDTO.setBalance(BigDecimal.valueOf(141232));




    }

}
