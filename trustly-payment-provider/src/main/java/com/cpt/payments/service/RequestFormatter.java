package com.cpt.payments.service;

import com.cpt.payments.http.HttpRequest;
import com.cpt.payments.pojo.request.TrustlyProviderRequest;

public interface RequestFormatter {

	HttpRequest prepareRequest(TrustlyProviderRequest trustlyProviderRequest);
}
