package com.banking.simplebankingapps.modules.customermanagement.dto;

import com.banking.simplebankingapps.modules.customermanagement.domain.model.Customer;
import com.banking.simplebankingapps.shared.IdentityCardType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDTO {
    private Long id;
    @NotEmpty(message = "First Name Field must not be empty")
    private String firstName;
    @NotEmpty(message = "Last Name Field must not be empty")
    private String lastName;
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE,message = "Email should be in valid format")
    private String email;
    @NotEmpty(message = "Address must not be empty")
    private String address;
    @NotEmpty(message = "Phone Number must not be empty")
    private String phoneNumber;
    @NotNull(message = "Identity Type must not be empty")
    private IdentityCardType identityType;
    @NotEmpty(message = "Identity Number must not be empty")
    private String identityNumber;

    public Customer customerDTOtoCustomerDomain() {
        Customer customer = new Customer();
        customer.setId(this.id);
        customer.setFirstName(this.firstName);
        customer.setLastName(this.lastName);
        customer.setEmail(this.email);
        customer.setAddress(this.address);
        customer.setPhoneNumber(this.phoneNumber);
        customer.setIdentityType(this.identityType);
        customer.setIdentityNumber(this.identityNumber);
        return customer;
    }

    public static CustomerDTO fromDomain(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setIdentityType(IdentityCardType.valueOf(String.valueOf(customer.getIdentityType())));
        customerDTO.setIdentityNumber(customer.getIdentityNumber());
        return customerDTO;
    }
}
