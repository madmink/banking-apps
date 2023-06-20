package com.banking.simplebankingapps.modules.transactionmanagement.exception;

public class TransactionManagementException extends RuntimeException {
    public TransactionManagementException(String message) {
        super(message);
    }

    public TransactionManagementException(String message, Throwable cause) {
        super(message, cause);
    }
}
