package com.cpt.payments.service;

import com.cpt.payments.pojo.request.TrustlyProviderRequest;
import com.cpt.payments.pojo.response.TrustlyProviderResponse;

public interface PaymentService {

	TrustlyProviderResponse initiatePayment(TrustlyProviderRequest trustlyProviderRequest);

}
