package com.banking.simplebankingapps.modules.accountmanagement.exception;

public class AccountManagementException extends RuntimeException {

    public AccountManagementException(String message) {
        super(message);
    }

    public AccountManagementException(String message, Throwable cause) {
        super(message, cause);
    }
}
