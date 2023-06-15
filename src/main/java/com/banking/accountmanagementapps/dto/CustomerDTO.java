package com.banking.accountmanagementapps.dto;

import com.banking.accountmanagementapps.entity.CustomerEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDTO {
    private Long id;
    @NotEmpty(message = "Field must not be empty")
    private String firstName;
    @NotEmpty(message = "Field must not be empty")
    private String lastName;
    @Email(message = "Email should be in valid format")
    private String email;
    @NotEmpty(message = "Field must not be empty")
    private String address;
    @NotEmpty(message = "Field must not be empty")
    private String phoneNumber;
    @NotEmpty(message = "Identity Type must not be empty")
    private String identityType;
    @NotEmpty(message = "Field must not be empty")
    private String identityNumber;

    public CustomerEntity toEntity() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(this.id);
        customerEntity.setFirstName(this.firstName);
        customerEntity.setLastName(this.lastName);
        customerEntity.setEmail(this.email);
        customerEntity.setAddress(this.address);
        customerEntity.setPhoneNumber(this.phoneNumber);
        customerEntity.setIdentityType(this.identityType);
        customerEntity.setIdentityNumber(this.identityNumber);
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
        customerDTO.setIdentityType(customerEntity.getIdentityType());
        customerDTO.setIdentityNumber(customerEntity.getIdentityNumber());
        return customerDTO;
    }
}


