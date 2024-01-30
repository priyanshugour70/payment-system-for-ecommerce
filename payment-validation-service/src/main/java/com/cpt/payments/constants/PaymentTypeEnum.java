package com.cpt.payments.constants;

import lombok.Getter;

public enum PaymentTypeEnum {

	SALE(1, "SALE"), REFUND(2, "REFUND");

	@Getter
	private Integer paymentTypeId;
	@Getter
	private String paymentTypeName;

	private PaymentTypeEnum(Integer paymentTypeId, String paymentTypeName) {
		this.paymentTypeId = paymentTypeId;
		this.paymentTypeName = paymentTypeName;
	}

	public static PaymentTypeEnum getPaymentType(String paymentType) {
		for (PaymentTypeEnum e : PaymentTypeEnum.values()) {
			if (paymentType.equals(e.paymentTypeName))
				return e;
		}
		return null;
	}
}
