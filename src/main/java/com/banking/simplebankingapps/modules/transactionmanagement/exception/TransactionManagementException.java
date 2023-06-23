package com.banking.simplebankingapps.modules.transactionmanagement.exception;

public class TransactionManagementException extends RuntimeException {
    private String errorCode;

    public TransactionManagementException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public TransactionManagementException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
