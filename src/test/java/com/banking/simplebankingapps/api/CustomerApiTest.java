package com.banking.simplebankingapps.api;

import com.banking.simplebankingapps.api.dto.CustomerDTO;
import com.banking.simplebankingapps.modules.customermanagement.domain.repository.CustomerRepository;
import com.banking.simplebankingapps.modules.customermanagement.exception.CustomerManagementException;
import com.banking.simplebankingapps.modules.customermanagement.infrastructure.entity.CustomerEntity;
import com.banking.simplebankingapps.modules.customermanagement.service.CustomerManagementApplicationService;
import com.banking.simplebankingapps.shared.IdentityCardType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CustomerApiTest {
    @InjectMocks
    private CustomerApi customerApi;

    @Mock
    private CustomerManagementApplicationService customerManagementApplicationService;

    @Mock
    private CustomerRepository customerRepository;

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
    class AllGetCustomerTest{
        @Test
        void testGetCustomerSuccessful(){
            CustomerDTO expectedCustomer = new CustomerDTO();
            expectedCustomer.setId(2L);

            when(customerManagementApplicationService.getCustomerById(expectedCustomer.getId())).thenReturn(expectedCustomer);
            ResponseEntity<Object> responseEntity = customerApi.getCustomerById(expectedCustomer.getId());

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(expectedCustomer, responseEntity.getBody());
            verify(customerManagementApplicationService).getCustomerById(expectedCustomer.getId());

        }

        @Test
        void testGetCustomer_IdNotFound() throws Exception{
            CustomerDTO expectedCustomer = new CustomerDTO();
            expectedCustomer.setId(2L);



        }
    }
}
