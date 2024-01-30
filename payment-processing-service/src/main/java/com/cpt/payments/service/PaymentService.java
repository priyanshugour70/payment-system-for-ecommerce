package com.cpt.payments.service;

import com.cpt.payments.pojo.PaymentResponse;
import com.cpt.payments.pojo.ProcessingServiceRequest;

public interface PaymentService {

	PaymentResponse processPayment(ProcessingServiceRequest processingServiceRequest);
}
