package com.cpt.payments.pojo.provider.response;

import lombok.Data;

@Data
public class TrustlyProviderResponse {
	private String paymentId;
	private String redirectUrl;
}
