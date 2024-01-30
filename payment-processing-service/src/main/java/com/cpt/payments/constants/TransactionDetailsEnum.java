package com.cpt.payments.constants;

import lombok.Getter;

public enum TransactionDetailsEnum {

	CREATED("000.001"), PENDING("000.002"), APPROVED("000.003"), FAILED("000.004");
	
	@Getter
	private String code;
	
	private TransactionDetailsEnum(String code) {
		this.code=code;
	}
}
