package com.cpt.payments.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpt.payments.constants.ControllerEndpoints;
import com.cpt.payments.pojo.request.TrustlyProviderRequest;
import com.cpt.payments.pojo.response.TrustlyProviderResponse;
import com.cpt.payments.service.PaymentService;
import com.cpt.payments.util.LogMessage;

@RestController
@RequestMapping(ControllerEndpoints.PAYMENT_BASE_URI)
public class PaymentController {
	
	private static final Logger LOGGER = LogManager.getLogger(PaymentController.class);
	
	@Autowired
	private PaymentService paymentService;

	@PostMapping(ControllerEndpoints.PROCESS_PAYMENT)
	ResponseEntity<TrustlyProviderResponse>  initiatePayment(@RequestBody TrustlyProviderRequest trustlyProviderRequest) {
		LogMessage.setLogMessagePrefix(ControllerEndpoints.PROCESS_PAYMENT);
		
		LogMessage.log(LOGGER, " processing trustly payment with request ::: " + trustlyProviderRequest);
		return new ResponseEntity<>(paymentService.initiatePayment(trustlyProviderRequest), HttpStatus.OK);
	}
}
