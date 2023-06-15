package com.banking.accountmanagementapps.repository;

import com.banking.accountmanagementapps.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long>{
    List<CustomerEntity> findAllById(Long customerId);
    Optional<CustomerEntity> findByIdentityNumber(String identityNumber);


}
