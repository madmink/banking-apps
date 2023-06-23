package com.banking.simplebankingapps.modules.accountmanagement.service;

public interface CustomerProvider {

    CustomerProviderDTO getCustomer(Long id);

    record CustomerProviderDTO(String name) {
    }
}

