package com.cpt.payments.pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
	private String errorCode;
	private String errorMessage;
	private boolean tpProviderError;
}