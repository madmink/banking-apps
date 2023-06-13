package com.banking.accountmanagementapps.repository;

import com.banking.accountmanagementapps.entity.AccountEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<AccountEntity, Long> {
    List<AccountEntity> findAllByAccountNumber(String accountNumber);
    Optional<AccountEntity> findByAccountNumber(String accountNumber);
    List<AccountEntity> findAllByCustomerId(Long customerId);
}
