package com.cpt.payments.constants;

import lombok.Getter;

public enum ErrorCodeEnum {
	GENERIC_EXCEPTION("30001","Something went wrong, please try later"), 
	FAILED_TO_CONNECT_TO_TRUSTLY("30002","Failed to connect to trustly provider"),
	FAILED_TO_INITIATE_PAYMENT_AT_TRUSTLY("30003","Failed to initiate payment at trustly"),
	
	ERROR_UNABLE_TO_VERIFY_RSA_SIGNATURE("636", "ERROR_UNABLE_TO_VERIFY_RSA_SIGNATURE");
	
	@Getter
	private String errorCode;
	@Getter
	private String errorMessage;
	
	private ErrorCodeEnum(String errorCode, String errorMessage) {
		this.errorCode=errorCode;
		this.errorMessage=errorMessage;
	}
	
}
