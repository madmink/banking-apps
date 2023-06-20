package com.banking.simplebankingapps.modules.customermanagement.domain.repository;

import com.banking.simplebankingapps.modules.customermanagement.infrastructure.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByIdentityNumber(String identityNumber);
}
