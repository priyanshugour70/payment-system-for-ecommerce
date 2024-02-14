package com.cpt.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProviderRequestResponseDto {

	private String transactionReference;
	private String request;
	private String response;
	private int status;
}
