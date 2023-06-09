package com.banking.accountmanagementapps.repository;

import com.banking.accountmanagementapps.entity.TransactionEntity;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {
}
