package com.banking.accountmanagementapps.ControllerTest;

import com.banking.accountmanagementapps.controller.CustomerController;
import com.banking.accountmanagementapps.dto.CustomerDTO;
import com.banking.accountmanagementapps.service.CustomerService;
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

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setFirstName("John");
        customerDTO.setLastName("Travolta");
        customerDTO.setEmail("john.travolta@gmail.com");
        customerDTO.setAddress("Sesame Street");
        customerDTO.setPhoneNumber("0851293128132");
        customerDTO.setIdentityType("KTP");
        customerDTO.setIdentityNumber("135124125412432");

        when(customerService.createCustomer(any(CustomerDTO.class))).thenReturn(customerDTO);

        mockMvc.perform(post("/api/customer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(customerDTO)));

        verify(customerService, times(1)).createCustomer(any(CustomerDTO.class));
    }

    @Test
    void testGetCustomerById() throws Exception {
        Long id = 1L;
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(id);


        when(customerService.getCustomerById(anyLong())).thenReturn(Collections.singletonList(customerDTO));

        mockMvc.perform(get("/api/customer/" + customerDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(customerDTO))));

        verify(customerService, times(1)).getCustomerById(anyLong());
    }

    @Test
    void testUpdateCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Mink");

        when(customerService.updateCustomer(any(CustomerDTO.class), anyLong())).thenReturn(customerDTO);

        mockMvc.perform(put("/api/customer/update/{customerId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDTO))).andExpect(status().isOk()).andExpect(content().json(objectMapper.writeValueAsString(customerDTO)));
    }

    @Test
    void deleteCustomer() throws Exception {


        doNothing().when(customerService).deleteCustomer(anyLong());

        mockMvc.perform(delete("/api/customer/delete/{customerId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}
