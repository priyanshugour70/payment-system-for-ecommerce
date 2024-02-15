package com.cpt.payments.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrustlyNotificationRequest {
	private String paymentId;
	private String status;
	private String code;
	private String message;
}
