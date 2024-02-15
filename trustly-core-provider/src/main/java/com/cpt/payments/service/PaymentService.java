package com.cpt.payments.service;

import com.cpt.payments.pojo.request.CoreTrustlyProvider;
import com.cpt.payments.pojo.request.TrustlyProviderRequest;
import com.cpt.payments.pojo.response.TrustlyCoreResponse;
import com.cpt.payments.pojo.response.TrustlyProviderResponse;

public interface PaymentService {

	TrustlyCoreResponse initiatePayment(CoreTrustlyProvider trustlyProviderRequest);

	void processPayment(String paymentId, String success);

}
