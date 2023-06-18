package com.banking.accountmanagementapps.service.impl;

import com.banking.accountmanagementapps.dto.CustomerDTO;
import com.banking.accountmanagementapps.entity.AccountEntity;
import com.banking.accountmanagementapps.entity.CustomerEntity;
import com.banking.accountmanagementapps.exception.BusinessException;
import com.banking.accountmanagementapps.repository.AccountRepository;
import com.banking.accountmanagementapps.repository.CustomerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @InjectMocks
    CustomerServiceImpl customerServiceImpl;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Nested
    class AllTestCreateCustomer {


        @Test
        void TestCreateCustomer() {
            CustomerDTO dto = new CustomerDTO();
            dto.setId(1L);
            // ... set other properties ...

            CustomerEntity entity = new CustomerEntity();
            entity.setId(1L);
            // ... set properties similar to dto ...

            when(customerRepository.save(any(CustomerEntity.class))).thenReturn(entity);

            CustomerDTO result = customerServiceImpl.createCustomer(dto);
            assertEquals(dto.getId(), result.getId());
        }

        @Test
        void TestCreateCustomer_identityNumberAlreadyUsed() {
            CustomerDTO dto = new CustomerDTO();
            dto.setId(1L);
            dto.setIdentityNumber("ID123");
            CustomerEntity entity = new CustomerEntity();
            entity.setIdentityNumber("ID123");
            when(customerRepository.findByIdentityNumber(anyString())).thenReturn(Optional.of(entity));

            BusinessException exception = assertThrows(BusinessException.class, () -> {
                customerServiceImpl.createCustomer(dto);
            });
            assertEquals("IDENTITY_NUMBER_ALREADY_USED", exception.getErrors().get(0).getCode());

        }
    }

    @Test
    void TestGetCustomerById() {
        Long id = 1L;
        CustomerEntity entity = new CustomerEntity();
        // ... set properties ...

        List<CustomerEntity> entities = Collections.singletonList(entity);
        when(customerRepository.findAllById(id)).thenReturn(entities);

        List<CustomerDTO> result = customerServiceImpl.getCustomerById(id);
        assertEquals(1, result.size());
    }

    @Nested
    class AllTestUpdateCustomer {
        @Test
        void TestUpdateCustomer() {
            CustomerDTO dto = new CustomerDTO();
            dto.setId(1L);
            // ... set other properties ...

            CustomerEntity entity = new CustomerEntity();
            // ... set properties similar to dto ...

            when(customerRepository.findById(anyLong())).thenReturn(Optional.of(entity));
            when(customerRepository.save(any(CustomerEntity.class))).thenReturn(entity);

            CustomerDTO result = customerServiceImpl.updateCustomer(dto, dto.getId());
            assertEquals(dto.getId(), result.getId());
        }

        @Test
        void TestUpdateCustomer_NotFound() {
            // Given
            CustomerDTO customerDTO = new CustomerDTO();
            long customerId = 1L;

            // When
            when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

            // Then
            assertThrows(BusinessException.class, () -> {
                customerServiceImpl.updateCustomer(customerDTO, customerId);
            });
        }


        @Nested
        class AllTestDeleteCustomer {


            @Test
            void TestDeleteCustomer() {
                CustomerEntity entity = new CustomerEntity();
                // ... set properties ...

                when(customerRepository.findById(anyLong())).thenReturn(Optional.of(entity));
                when(accountRepository.findAllByCustomerId(anyLong())).thenReturn(Collections.emptyList());

                assertDoesNotThrow(() -> customerServiceImpl.deleteCustomer(1L));
            }

            @Test
            void TestDeleteCustomer_NotFound() {
                when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

                BusinessException exception = assertThrows(BusinessException.class, () -> {
                    customerServiceImpl.deleteCustomer(1L);
                });

                assertEquals("CUSTOMER_NOT_FOUND", exception.getErrors().get(0).getCode());
            }

            @Test
            void TestDeleteCustomer_AccountExist() {
                CustomerEntity entity = new CustomerEntity();
                // ... set properties ...

                when(customerRepository.findById(anyLong())).thenReturn(Optional.of(entity));
                when(accountRepository.findAllByCustomerId(anyLong())).thenReturn(Collections.singletonList(new AccountEntity()));

                BusinessException exception = assertThrows(BusinessException.class, () -> {
                    customerServiceImpl.deleteCustomer(1L);
                });

                assertEquals("CUSTOMER_HAS_UNRESOLVED_ACCOUNT", exception.getErrors().get(0).getCode());
            }
        }
    }
}
