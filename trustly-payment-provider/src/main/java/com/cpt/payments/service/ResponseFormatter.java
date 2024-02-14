package com.cpt.payments.service;

import org.springframework.http.ResponseEntity;

import com.cpt.payments.pojo.response.TrustlyProviderResponse;

public interface ResponseFormatter {

	TrustlyProviderResponse processResponse(ResponseEntity<String> providerResponse);
}
