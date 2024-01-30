package com.cpt.payments.pojo;

import lombok.Data;

@Data
public class PaymentRequest {
    private String paymentReference;
    private String creditorAccount;
    private String debitorAccount;
}
