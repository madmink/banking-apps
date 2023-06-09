package com.banking.accountmanagementapps.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "CUSTOMER_TABLE")
@Getter
@Setter
@NoArgsConstructor

public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;
    private String identityType;
    private String identityNumber;

    @OneToMany(mappedBy = "customer")
    private List<AccountEntity> accounts;

}
