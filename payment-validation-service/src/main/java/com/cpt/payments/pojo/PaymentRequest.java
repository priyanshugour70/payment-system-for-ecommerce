package com.cpt.payments.pojo;

import lombok.Data;

@Data
public class PaymentRequest {
	private User user;
	private Payment payment;
}
