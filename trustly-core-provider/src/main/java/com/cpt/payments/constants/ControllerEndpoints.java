package com.cpt.payments.constants;

public class ControllerEndpoints {
	public static final String PAYMENT_BASE_URI = "/payment";
	
	public static final String PROCESS_PAYMENT = "/initiate";

	public static final String SUCCESS_PAYMENT = "/success/{paymentId}";

	public static final String FAIL_PAYMENT = "/fail/{paymentId}";
}
