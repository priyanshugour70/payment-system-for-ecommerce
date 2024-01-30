package com.cpt.payments.pojo.provider.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrustlyProviderRequest {
	private String transactionReference;
	private String creditorNumber;
	private String debitorNumber;
	private String currency;
	private double amount;
	private String firstName;
	private String lastName;
	private String email;
}
