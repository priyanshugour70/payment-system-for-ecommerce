package com.cpt.payments.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpt.payments.constants.ControllerEndpoints;
import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.exception.ValidationException;
import com.cpt.payments.pojo.request.CoreTrustlyProvider;
import com.cpt.payments.pojo.response.TrustlyCoreResponse;
import com.cpt.payments.service.PaymentService;
import com.cpt.payments.util.LogMessage;

@RestController
@RequestMapping(ControllerEndpoints.PAYMENT_BASE_URI)
public class PaymentController {
	private static final String SUCCESS = "SUCCESS";

	private static final Logger LOGGER = LogManager.getLogger(PaymentController.class);

	private static final String FAIL = "FAILED";

	@Autowired
	private PaymentService paymentService;

	@PostMapping(ControllerEndpoints.PROCESS_PAYMENT)
	ResponseEntity<TrustlyCoreResponse> initiatePayment(@RequestBody CoreTrustlyProvider trustlyProviderRequest) {
		LogMessage.setLogMessagePrefix("/TRUSLTY_CORE_PROCESS_PAYMENT:");
		LogMessage.log(LOGGER, " processing trustly payment with request ::: " + trustlyProviderRequest);
		
		return new ResponseEntity<>(paymentService.initiatePayment(trustlyProviderRequest), HttpStatus.OK);
	}

	@PostMapping(ControllerEndpoints.SUCCESS_PAYMENT)
	ResponseEntity<Void> sucessPayment(@PathVariable("paymentId") String paymentId) {
		LogMessage.log(LOGGER, " success payment for -> " + paymentId);
		paymentService.processPayment(paymentId, SUCCESS);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(ControllerEndpoints.FAIL_PAYMENT)
	ResponseEntity<Void> failPayment(@PathVariable("paymentId") String paymentId) {
		LogMessage.log(LOGGER, " success payment for -> " + paymentId);
		paymentService.processPayment(paymentId, FAIL);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
