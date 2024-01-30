package com.cpt.payments.dao;

import com.cpt.payments.dto.TransactionDetails;

public interface TransactionDetailsDao {

	public TransactionDetails getTransactionDetailsById(String code);
}
