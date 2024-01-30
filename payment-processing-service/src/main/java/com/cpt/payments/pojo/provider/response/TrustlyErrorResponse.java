package com.cpt.payments.pojo.provider.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrustlyErrorResponse {
	private String errorCode;
	private String errorMessage;
	private boolean tpProviderError;
}