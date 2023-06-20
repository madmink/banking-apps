package com.banking.simplebankingapps.api.globalexceptionhandler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorModel {
    private String code;
    private String message;
}
