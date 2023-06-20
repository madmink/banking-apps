package com.banking.simplebankingapps.modules.customermanagement.exception;

public class CustomerManagementException extends RuntimeException {
    private String errorCode;

    public CustomerManagementException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
