package com.banking.accountmanagementapps.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class CustomExceptionHandlerTest {

    @InjectMocks
    private CustomExceptionHandler customExceptionHandler;

    @Mock
    private BusinessException businessException;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void shouldHandleBusinessException() {
        ErrorModel errorModel = new ErrorModel();
        errorModel.setCode("CODE");
        errorModel.setMessage("message");

        List<ErrorModel> errors = Arrays.asList(errorModel);

        when(businessException.getErrors()).thenReturn(errors);

        ResponseEntity<List<ErrorModel>> response = customExceptionHandler.handleBusinessException(businessException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errors, response.getBody());
    }

    @Test
    void shouldHandleFieldValidation() {
        Object target = new Object();
        String objectName = "object";
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(target, objectName);
        bindingResult.addError(new FieldError(objectName, "field", "defaultMessage"));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException((MethodParameter) null, bindingResult);
        CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();

        ResponseEntity<List<ErrorModel>> response = customExceptionHandler.handleFieldValidation(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("field", response.getBody().get(0).getCode());
        assertEquals("defaultMessage", response.getBody().get(0).getMessage());
    }

}
