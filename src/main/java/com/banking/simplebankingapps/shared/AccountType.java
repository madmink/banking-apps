package com.banking.simplebankingapps.shared;

import lombok.Getter;

@Getter
public enum AccountType {
    SAVINGS("Savings"),
    CHECKING("Checking");

    private final String name;

    AccountType(String name) {
        this.name = name;
    }
}
