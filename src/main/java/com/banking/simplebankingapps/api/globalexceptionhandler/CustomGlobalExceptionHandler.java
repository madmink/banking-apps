package com.banking.simplebankingapps.api.globalexceptionhandler;

import com.banking.simplebankingapps.modules.customermanagement.exception.CustomerManagementException;
import com.banking.simplebankingapps.modules.accountmanagement.exception.AccountManagementException;
import com.banking.simplebankingapps.modules.transactionmanagement.exception.TransactionManagementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomGlobalExceptionHandler {

    // Handle exceptions thrown by Customer Management
    @ExceptionHandler(CustomerManagementException.class)
    public final ResponseEntity<CustomErrorType> handleCustomerManagementException(CustomerManagementException ex, WebRequest request) {
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add(ex.getMessage());
        CustomErrorType errorDetails = new CustomErrorType(ex.getErrorCode(), errorMessages);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // Handle exceptions thrown by Account Management
    @ExceptionHandler(AccountManagementException.class)
    public ResponseEntity<CustomErrorType> handleAccountManagementException(AccountManagementException ex) {
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add(ex.getMessage());
        return new ResponseEntity<>(new CustomErrorType("ACC_MAN_EXC", errorMessages), HttpStatus.BAD_REQUEST);
    }

    // Handle exceptions thrown by Transaction Management
    @ExceptionHandler(TransactionManagementException.class)
    public ResponseEntity<CustomErrorType> handleTransactionManagementException(TransactionManagementException ex) {
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add(ex.getMessage());
        return new ResponseEntity<>(new CustomErrorType("TRAN_MAN_EXC", errorMessages), HttpStatus.BAD_REQUEST);
    }

    // Handle method argument not valid exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorType> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorMessages.add(error.getDefaultMessage());
        }
        String errorCode = "VALIDATION_FAILED";
        return new ResponseEntity<>(new CustomErrorType(errorCode, errorMessages), HttpStatus.BAD_REQUEST);
    }
}
