package com.cpt.payments.service;

import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.pojo.PaymentResponse;

public interface PaymentService {

	PaymentResponse validateAndInitiatePayment(PaymentRequest paymentRequest);

}
