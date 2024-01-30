package com.cpt.payments.constants;

import lombok.Getter;

public enum PaymentMethodEnum {

	APM(1, "APM");

	@Getter
	private Integer paymentMethodId;
	@Getter
	private String paymentMethodName;

	private PaymentMethodEnum(Integer paymentMethodId, String paymentMethodName) {
		this.paymentMethodId = paymentMethodId;
		this.paymentMethodName = paymentMethodName;
	}

	public static PaymentMethodEnum getPaymentMethod(String paymentMethod) {
		for (PaymentMethodEnum e : PaymentMethodEnum.values()) {
			if (paymentMethod.equalsIgnoreCase(e.paymentMethodName))
				return e;
		}
		return null;
	}
}
