package com.banking.simplebankingapps.modules.accountmanagement.exception;

public class AccountManagementException extends RuntimeException {
    private String errorCode;

    public AccountManagementException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public AccountManagementException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
