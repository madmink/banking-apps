package com.banking.accountmanagementapps.repository;

import com.banking.accountmanagementapps.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long>{
}
