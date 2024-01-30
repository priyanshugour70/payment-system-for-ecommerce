package com.cpt.payments.pojo;

import lombok.Data;

@Data
public class TrustlyNotificationRequest {
	private String paymentId;
	private String status;
	private String code;
	private String message;
}
