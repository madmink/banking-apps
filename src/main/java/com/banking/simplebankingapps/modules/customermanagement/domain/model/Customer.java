package com.banking.simplebankingapps.modules.customermanagement.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.banking.simplebankingapps.modules.customermanagement.infrastructure.entity.CustomerEntity;
import com.banking.simplebankingapps.api.dto.CustomerDTO;
import com.banking.simplebankingapps.shared.IdentityCardType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;
    private IdentityCardType identityType;
    private String identityNumber;

    public CustomerEntity toCustomerEntity() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(this.id);
        customerEntity.setFirstName(this.firstName);
        customerEntity.setLastName(this.lastName);
        customerEntity.setEmail(this.email);
        customerEntity.setAddress(this.address);
        customerEntity.setPhoneNumber(this.phoneNumber);
        if (this.identityType != null) {
            customerEntity.setIdentityType(this.identityType.name());
        }
        customerEntity.setIdentityNumber(this.identityNumber);

        return customerEntity;
    }

    public static Customer fromCustomerEntity(CustomerEntity customerEntity) {
        Customer customer = new Customer();
        customer.setId(customerEntity.getId());
        customer.setFirstName(customerEntity.getFirstName());
        customer.setLastName(customerEntity.getLastName());
        customer.setEmail(customerEntity.getEmail());
        customer.setAddress(customerEntity.getAddress());
        customer.setPhoneNumber(customerEntity.getPhoneNumber());
        customer.setIdentityType(IdentityCardType.valueOf(customerEntity.getIdentityType()));
        customer.setIdentityNumber(customerEntity.getIdentityNumber());

        return customer;
    }
    public void updateFromDto(CustomerDTO customerDto) {
        this.firstName = customerDto.getFirstName();
        this.lastName = customerDto.getLastName();
        this.email = customerDto.getEmail();
        this.address = customerDto.getAddress();
        this.phoneNumber = customerDto.getPhoneNumber();
        this.identityType = customerDto.getIdentityType();
        this.identityNumber = customerDto.getIdentityNumber();
    }
}
