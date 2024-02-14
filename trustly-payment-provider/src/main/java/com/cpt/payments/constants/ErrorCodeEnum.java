package com.cpt.payments.constants;

import lombok.Getter;

public enum ErrorCodeEnum {
	GENERIC_EXCEPTION("30001","Something went wrong, please try later"), 
	FAILED_TO_CONNECT_TO_TRUSTLY("30002","Failed to connect to trustly provider"),
	FAILED_TO_INITIATE_PAYMENT_AT_TRUSTLY("30003","Failed to initiate payment at trustly"),
	FAILED_TO_CREATE_SIGNATURE("30004","Failed to create trustly signature" ),
	FAILED_TO_PROCESS_TRUSTLY_SIGNATURE("30005","Failed to process trustly signature" );
	
	@Getter
	private String errorCode;
	@Getter
	private String errorMessage;
	
	private ErrorCodeEnum(String errorCode, String errorMessage) {
		this.errorCode=errorCode;
		this.errorMessage=errorMessage;
	}
	
}
