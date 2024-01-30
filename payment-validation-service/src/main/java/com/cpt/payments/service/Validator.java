package com.cpt.payments.service;

import com.cpt.payments.pojo.PaymentRequest;

public interface Validator {
	void doValidate(PaymentRequest paymentRequest);
}
