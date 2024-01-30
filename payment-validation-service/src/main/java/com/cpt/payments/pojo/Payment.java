package com.cpt.payments.pojo;

import lombok.Data;

@Data
public class Payment {
	 	private String paymentMethod;
	    private String paymentType;
	    private String amount;
	    private String currency;
	    private String merchantTransactionReference;
	    private String providerId;
	    private String creditorAccount;
	    private String debitorAccount;
}
