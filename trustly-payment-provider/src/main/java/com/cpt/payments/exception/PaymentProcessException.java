package com.cpt.payments.exception;

import org.springframework.http.HttpStatus;

public class PaymentProcessException extends RuntimeException {
	private static final long serialVersionUID = -2171272011475853092L;
	private final HttpStatus httpStatus;
	private final String errorCode;
	private final String errorMessage;
	private final boolean tpProviderError;

	public PaymentProcessException(HttpStatus httpStatus, 
			String errorCode, String errorMessage,
			boolean tpProviderError) {
		super();
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.tpProviderError = tpProviderError;
	}
	
	public PaymentProcessException(HttpStatus httpStatus, 
			String errorCode, String errorMessage) {
		super();
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.tpProviderError = false;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public boolean isTpProviderError() {
		return tpProviderError;
	}
}
