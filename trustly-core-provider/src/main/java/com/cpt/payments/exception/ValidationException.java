package com.cpt.payments.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends RuntimeException {
	private static final long serialVersionUID = -2171272011475853092L;
	private HttpStatus httpStatus;
	private String errorCode;
	private String errorMessage;
	private String uuid;
	private String method;
	

	public ValidationException(HttpStatus httpStatus, String errorCode, String errorMessage, 
			String uuid, String method) {
		super();
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.uuid = uuid;
		this.method = method;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
}
