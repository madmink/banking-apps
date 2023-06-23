package com.banking.simplebankingapps.modules.customermanagement.service;
import com.banking.simplebankingapps.modules.accountmanagement.infrastructure.entity.AccountEntity;

import java.util.List;

public interface AccountProvider {

    AccountProviderDTO provideAccountById(Long customerId);

    record AccountProviderDTO(List<AccountEntity> accounts) {
        public List<AccountEntity> provideAccounts() {
            return accounts;
        }
    }
}
