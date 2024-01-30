package com.cpt.payments.service;

import com.cpt.payments.dto.Transaction;
import com.cpt.payments.pojo.PaymentResponse;
import com.cpt.payments.pojo.ProcessingServiceRequest;

public interface ProviderHandler {

	PaymentResponse processPayment(Transaction transaction, ProcessingServiceRequest processingServiceRequest);

}
