package com.banking.simplebankingapps.modules.accountmanagement.domain.repository;

import com.banking.simplebankingapps.modules.accountmanagement.domain.model.Account;
import com.banking.simplebankingapps.modules.accountmanagement.infrastructure.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository <AccountEntity, Long>{
    Optional<AccountEntity> findByAccountNumber(String accountNumber);
    List<AccountEntity> findAllByCustomerId(Long customerId);
}
