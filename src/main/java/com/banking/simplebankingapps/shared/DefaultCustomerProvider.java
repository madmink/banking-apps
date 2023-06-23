package com.banking.simplebankingapps.shared;

import com.banking.simplebankingapps.modules.customermanagement.dto.CustomerDTO;
import com.banking.simplebankingapps.modules.customermanagement.service.CustomerManagementService;
import org.springframework.stereotype.Service;

import com.banking.simplebankingapps.modules.accountmanagement.service.CustomerProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultCustomerProvider implements CustomerProvider {

    private final CustomerManagementService customerManagementService;

    @Override
    public CustomerProviderDTO getCustomer(Long id) {
        CustomerDTO customer = customerManagementService.getCustomerById(id);

        return new CustomerProviderDTO(customer.getFirstName() + " " + customer.getLastName());
    }


    }

