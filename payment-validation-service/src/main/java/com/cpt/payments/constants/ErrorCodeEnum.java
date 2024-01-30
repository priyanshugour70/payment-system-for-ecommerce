package com.cpt.payments.constants;

import lombok.Getter;

public enum ErrorCodeEnum {
	SIGNATURE_NOT_FOUND("10002","Bad request, signature not found"),
	GENERIC_EXCEPTION("10001","Something went wrong, please try later"),
	SIGNATURE_ALTERED("10003","Unauthorized request, data altered"),
	EXCEPTION_IN_SIGNATURE_CALCULATION("10004","Something went wrong, Signature calculation failed"),
	AMOUNT_VALIDATION_FAILED("10005","Bad request, given amount parameter is not valid or empty"),
	CURRENCY_VALIDATION_FAILED("10006","Bad request, given currency parameter is not valid or empty"),
	EMAIL_VALIDATION_FAILED("10007","Bad request, given email parameter is not valid or empty"),
	FIRSTNAME_VALIDATION_FAILED("10008","Bad request, given firstName parameter is not valid or empty"),
	LASTNAME_VALIDATION_FAILED("10009","Bad request, given lastName parameter is not valid or empty"),
	MERCHANT_TRANSACTION_REFERENCE_VALIDATION_FAILED("10010","Bad request, given merchantTransactionReference parameter is not valid or empty"),
	PAYMENT_METHOD_VALIDATION_FAILED("10011","Bad request, given paymentMethod parameter is not valid or empty"),
	PAYMENT_TYPE_VALIDATION_FAILED("10012","Bad request, given paymentType parameter is not valid or empty"),
	PAYMENT_REQUEST_VALIDATION_FAILED("10013","Bad request, given request is empty"),
	PAYMENT_VALIDATION_FAILED("10014","Bad request, given payment object is empty"),
	USER_VALIDATION_FAILED("10015","Bad request, given user object is empty"),
	PHONE_NUMBER_VALIDATION_FAILED("10016","Bad request, given phoneNumber parameter is not valid or empty"),
	PROVIDER_ID_VALIDATION_FAILED("10017","Bad request, given proiderId parameter is not valid or empty"),
	DUPLICATE_TRANSACTION("10018","Bad request, duplicate merchantTransactionReference"),
	FAILED_TO_CREATE_USER("10019", "Something went wrong, User creation failed"),
	FAILED_TO_CREATE_TRANSACTION("10020", "Something went wrong, Payment creation failed"),
	DEBITOR_ACCOUNT_NUMBER_VALIDATION_FAILED("10021","Bad request, given debitorAccountNumber parameter is not valid or empty"),
	CREDITOR_ACCOUNT_NUMBER_VALIDATION_FAILED("10022","Bad request, given creditorAccountNumber parameter is not valid or empty");
	
	@Getter
	private String errorCode;
	@Getter
	private String errorMessage;
	
	private ErrorCodeEnum(String errorCode, String errorMessage) {
		this.errorCode=errorCode;
		this.errorMessage=errorMessage;
	}
	
}
