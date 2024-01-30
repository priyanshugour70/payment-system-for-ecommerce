package com.cpt.payments.constants;

import lombok.Getter;

public enum TransactionStatusEnum {
	
	CREATED(1,"CREATED"),
	PENDING(2,"PENDING"),
	APPROVED(3,"APPROVED"),
	FAILED(4,"FAILED");
	
	@Getter
	private Integer id;
	
	@Getter
	private String name;

	private TransactionStatusEnum(Integer id, String name) {
		this.id =id;
		this.name=name;
	}
	
	public static TransactionStatusEnum getTransactionStatusEnum(int transactionStatusId) {
		for (TransactionStatusEnum e : TransactionStatusEnum.values()) {
			if (transactionStatusId == (e.id))
				return e;
		}
		return null;
	}
	
}
