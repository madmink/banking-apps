package com.banking.simplebankingapps.api.globalexceptionhandler;

import java.util.List;

public record CustomErrorType (String errorCode, List<String> errorMessage) {
}
