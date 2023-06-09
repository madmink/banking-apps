package com.banking.accountmanagementapps.dto;

import com.banking.accountmanagementapps.entity.CustomerEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;

    public CustomerEntity toEntity() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(this.id);
        customerEntity.setFirstName(this.firstName);
        customerEntity.setLastName(this.lastName);
        customerEntity.setEmail(this.email);
        customerEntity.setAddress(this.email);
        customerEntity.setPhoneNumber(this.phoneNumber);
        return customerEntity;
    }

    public static CustomerDTO fromEntity(CustomerEntity customerEntity){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customerEntity.getId());
        customerDTO.setFirstName(customerEntity.getFirstName());
        customerDTO.setLastName(customerEntity.getLastName());
        customerDTO.setEmail(customerEntity.getEmail());
        customerDTO.setAddress(customerEntity.getAddress());
        customerDTO.setPhoneNumber(customerEntity.getPhoneNumber());
        return customerDTO;
    }
}


