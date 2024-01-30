package com.cpt.payments.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import com.cpt.payments.exceptions.ValidationException;
import com.cpt.payments.http.HttpRestTemplateEngine;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.pojo.PaymentResponse;
import com.cpt.payments.pojo.TransactionReqRes;
import com.cpt.payments.service.impl.PaymentServiceImpl;
import com.cpt.payments.util.TestDataProviderUtil;
import com.google.gson.Gson;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private HttpRestTemplateEngine httpRestTemplateEngine;

    @Mock
    private ApplicationContext context;

    @Mock
    private Validator validator;

    private Gson gson = new Gson();

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    @DisplayName("Validate and Initiate Payment Successfully")
    public void validateAndInitiatePaymentSuccessfully() {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setUser(TestDataProviderUtil.getTestUserBean());
        paymentRequest.setPayment(TestDataProviderUtil.getTestPayment());

        TransactionReqRes transaction = new TransactionReqRes();
        transaction.setId(456);
        ResponseEntity<String> transactionResponse = ResponseEntity.ok(gson.toJson(transaction));

        PaymentResponse expectedPaymentResponse = new PaymentResponse();
        expectedPaymentResponse.setPaymentReference("testPaymentReference");
        ResponseEntity<String> paymentResponseEntity = ResponseEntity.ok(gson.toJson(expectedPaymentResponse));

        ReflectionTestUtils.setField(paymentService, "validators", "SIGNATURE_CHECK_FILTER,PAYMENT_REQUEST_FILTER,DUPLICATE_TXN_FILTER,MERCHANT_TXN_ID_FILTER,FIRST_NAME_FILTER,LAST_NAME_FILTER,CUSTOMER_EMAIL_FILTER,PHONE_NUMBER_FILTER,PAYMENT_METHOD_FILTER,PAYMENT_TYPE_FILTER,AMOUNT_FILTER,CURRENCY_FILTER,PROVIDER_ID_FILTER,CREDITOR_ACCOUNT_NUMBER,DEBITOR_ACCOUNT_NUMBER");
        ReflectionTestUtils.setField(paymentService, "context", context);

        when(userService.createUser(any())).thenReturn(123l);
        when(httpRestTemplateEngine.execute(any())).thenReturn(transactionResponse, paymentResponseEntity);
        when(context.getBean(any(Class.class))).thenReturn(validator);
        doNothing().when(validator).doValidate(any());

        PaymentResponse actualPaymentResponse = paymentService.validateAndInitiatePayment(paymentRequest);

        assertNotNull(actualPaymentResponse);
        assertEquals(expectedPaymentResponse.getPaymentReference(), actualPaymentResponse.getPaymentReference());
    }

    @Test
    @DisplayName("Initiate Transaction fails")
    public void validateAndInitiatePaymentInitFail() {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setUser(TestDataProviderUtil.getTestUserBean());
        paymentRequest.setPayment(TestDataProviderUtil.getTestPayment());

        ReflectionTestUtils.setField(paymentService, "validators", "SIGNATURE_CHECK_FILTER,PAYMENT_REQUEST_FILTER,DUPLICATE_TXN_FILTER,MERCHANT_TXN_ID_FILTER,FIRST_NAME_FILTER,LAST_NAME_FILTER,CUSTOMER_EMAIL_FILTER,PHONE_NUMBER_FILTER,PAYMENT_METHOD_FILTER,PAYMENT_TYPE_FILTER,AMOUNT_FILTER,CURRENCY_FILTER,PROVIDER_ID_FILTER,CREDITOR_ACCOUNT_NUMBER,DEBITOR_ACCOUNT_NUMBER");
        ReflectionTestUtils.setField(paymentService, "context", context);

        when(userService.createUser(any())).thenReturn(123l);
        when(context.getBean(any(Class.class))).thenReturn(validator);
        when(httpRestTemplateEngine.execute(any())).thenReturn(ResponseEntity.internalServerError().build());
        doNothing().when(validator).doValidate(any());

        assertThrows(ValidationException.class, () -> paymentService.validateAndInitiatePayment(paymentRequest));
    }

//    @Test
//    @DisplayName("Process Transaction fails")
//    public void validateAndInitiatePaymentProcessFail() {
//        PaymentRequest paymentRequest = new PaymentRequest();
//        paymentRequest.setUser(TestDataProviderUtil.getTestUserBean());
//        paymentRequest.setPayment(TestDataProviderUtil.getTestPayment());
//
//        Transaction transaction = new Transaction();
//        transaction.setId(456);
//        ResponseEntity<String> transactionResponse = ResponseEntity.ok(gson.toJson(transaction));
//
//        ReflectionTestUtils.setField(paymentService, "validators", "SIGNATURE_CHECK_FILTER,PAYMENT_REQUEST_FILTER,DUPLICATE_TXN_FILTER,MERCHANT_TXN_ID_FILTER,FIRST_NAME_FILTER,LAST_NAME_FILTER,CUSTOMER_EMAIL_FILTER,PHONE_NUMBER_FILTER,PAYMENT_METHOD_FILTER,PAYMENT_TYPE_FILTER,AMOUNT_FILTER,CURRENCY_FILTER,PROVIDER_ID_FILTER,CREDITOR_ACCOUNT_NUMBER,DEBITOR_ACCOUNT_NUMBER");
//        ReflectionTestUtils.setField(paymentService, "context", context);
//
//        when(userService.createUser(any())).thenReturn(123l);
//        when(context.getBean(any(Class.class))).thenReturn(validator);
//        when(httpRestTemplateEngine.execute(any())).thenReturn(transactionResponse, ResponseEntity.internalServerError().build());
//        doNothing().when(validator).doValidate(any());
//
//        assertThrows(ValidationException.class, () -> paymentService.validateAndInitiatePayment(paymentRequest));
//    }

}
