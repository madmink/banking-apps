package com.banking.accountmanagementapps.repository;

import com.banking.accountmanagementapps.entity.AccountEntity;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<AccountEntity, Long> {
}
