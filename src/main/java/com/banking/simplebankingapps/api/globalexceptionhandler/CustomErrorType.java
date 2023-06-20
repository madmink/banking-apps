package com.banking.simplebankingapps.api.globalexceptionhandler;

import java.util.List;

public class CustomErrorType {
    private String errorCode;
    private List<String> errorMessages;

    public CustomErrorType(String errorCode, List<String> errorMessages) {
        this.errorCode = errorCode;
        this.errorMessages = errorMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
