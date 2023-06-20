package com.banking.simplebankingapps.modules.transactionmanagement.domain.repository;

import com.banking.simplebankingapps.modules.transactionmanagement.infrastructure.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}
