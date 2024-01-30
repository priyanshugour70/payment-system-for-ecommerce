package com.cpt.payments.dao;

import com.cpt.payments.constants.MerchantPaymentSaveResult;
import com.cpt.payments.dto.MerchantPaymentRequest;

public interface MerchantPaymentRequestDao {

	MerchantPaymentRequest getMerchantPaymentRequest(String merchantTransactionId);

	MerchantPaymentSaveResult insertMerchantPaymentRequest(MerchantPaymentRequest merchantPaymentRequest);

}
