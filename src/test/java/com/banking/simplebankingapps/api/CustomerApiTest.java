package com.banking.simplebankingapps.api;

import com.banking.simplebankingapps.api.dto.CustomerDTO;
import com.banking.simplebankingapps.modules.customermanagement.exception.CustomerManagementException;
import com.banking.simplebankingapps.modules.customermanagement.service.CustomerManagementApplicationService;
import com.banking.simplebankingapps.shared.IdentityCardType;
import com.fasterxml.jackson.databind.ObjectMapper;
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


import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CustomerApiTest {
    @InjectMocks
    private CustomerApi customerApi;

    @Mock
    private CustomerManagementApplicationService customerManagementApplicationService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private CustomerDTO customerDTO;

    @BeforeEach
    void Setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerApi).build();
        objectMapper = new ObjectMapper();
        customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setFirstName("Test");
        customerDTO.setLastName("Ost Tyron");
        customerDTO.setEmail("Mank.Olenk@odading.com");
        customerDTO.setAddress("Sesame Street");
        customerDTO.setPhoneNumber("412314123");
        customerDTO.setIdentityType(IdentityCardType.KTP);
        customerDTO.setIdentityNumber("141231423");
    }

    @Nested
    class AllCreateCustomerTest {
        @Test
        void testCreateCustomerSuccessful() throws Exception {

            when(customerManagementApplicationService.createCustomer(any(CustomerDTO.class))).thenReturn(customerDTO);

            mockMvc.perform(post("/api/customer/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(customerDTO)))

                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(customerDTO)));

        }

        @Test
        void testCreateCustomer_Failure() throws Exception {

            String errorCode = "YOUR_BELOVED_ERROR_CODE";
            String errorMessage = "Customer could not be created.";

            when(customerManagementApplicationService.createCustomer(any(CustomerDTO.class)))
                    .thenThrow(new CustomerManagementException(errorCode, errorMessage));

            mockMvc.perform(post("/api/customer/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(customerDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errorCode", is(errorCode)))
                    .andExpect(jsonPath("$.errorMessages[0]", is(errorMessage)));
        }


    }

    @Nested
    class AllGetCustomerTest {
        @Test
        void testGetCustomerSuccessful() throws Exception {

            when(customerManagementApplicationService.getCustomerById(customerDTO.getId())).thenReturn(customerDTO);

            mockMvc.perform(get("/api/customer/detail/{customerId}", customerDTO.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(customerDTO)))
                    .andExpect(status().isOk());

        }

        @Test
        void testGetCustomer_IdNotFound() throws Exception {
            CustomerDTO expectedCustomer = new CustomerDTO();
            expectedCustomer.setId(2L);

            String errorCode = "YOUR_BELOVED_ERROR_CODE";
            String errorMessage = "Customer could not be created.";

            when(customerManagementApplicationService.getCustomerById(expectedCustomer.getId())).thenThrow(new CustomerManagementException(errorCode, errorMessage));

            mockMvc.perform(get("/api/customer/detail/{customerId}", 2L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(customerDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errorCode", is(errorCode)))
                    .andExpect(jsonPath("$.errorMessages[0]", is(errorMessage)));


        }
    }

    @Nested
    class AllUpdateCustomerTest {

        @Test
        void testUpdateCustomerSuccess() throws Exception {
            CustomerDTO newDataUpdate = new CustomerDTO();
            newDataUpdate.setFirstName("Put Ang In A");

            when(customerManagementApplicationService.updateCustomer(any(CustomerDTO.class), anyLong())).thenReturn(newDataUpdate);

            mockMvc.perform(put("/api/customer/update/{customerId}", 1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(newDataUpdate)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName", is(newDataUpdate.getFirstName())));
        }

        @Test
        void testUpdateCustomerFailure_CustomerIdNotFound() throws Exception {

            String errorCode = "YOUR_BELOVED_ERROR_CODE";
            String errorMessage = "Customer ID can not be found.";

            when(customerManagementApplicationService.updateCustomer(any(CustomerDTO.class), anyLong())).thenThrow(new CustomerManagementException(errorCode, errorMessage));

            mockMvc.perform(put("/api/customer/update/{customerId}", 1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(customerDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errorCode", is(errorCode)))
                    .andExpect(jsonPath("$.errorMessages[0]", is(errorMessage)));

        }

    }

    @Test
    void testDeleteCustomerSuccessful() throws Exception {
        doNothing().when(customerManagementApplicationService).deleteCustomer(anyLong());

        mockMvc.perform(delete("/api/customer/delete/{customerId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}

