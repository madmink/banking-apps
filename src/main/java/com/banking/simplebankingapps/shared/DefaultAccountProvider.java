package com.banking.simplebankingapps.shared;

import com.banking.simplebankingapps.modules.accountmanagement.domain.repository.AccountRepository;
import com.banking.simplebankingapps.modules.accountmanagement.infrastructure.entity.AccountEntity;
import com.banking.simplebankingapps.modules.customermanagement.service.AccountProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultAccountProvider implements AccountProvider {

    private final AccountRepository accountRepository;
    @Override
    public AccountProviderDTO provideAccountById(Long customerId) {
        List<AccountEntity> account = accountRepository.findAllByCustomerId(customerId);

        return new AccountProvider.AccountProviderDTO(account);
    }

}
