package com.banking.accountmanagementapps.repository;

import com.banking.accountmanagementapps.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long>{
    List<CustomerEntity> findAllById(Long customerId);


}
