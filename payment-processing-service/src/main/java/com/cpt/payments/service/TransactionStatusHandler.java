package com.cpt.payments.service;

import com.cpt.payments.dto.Transaction;

public abstract class TransactionStatusHandler {

	public abstract boolean updateStatus(Transaction transaction);
}
