package com.cpt.payments.service;

import com.cpt.payments.dto.Transaction;

public interface PaymentStatusService {

	Transaction updatePaymentStatus(Transaction transaction);

}
