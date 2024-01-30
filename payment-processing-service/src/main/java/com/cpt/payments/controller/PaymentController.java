package com.cpt.payments.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpt.payments.constants.Endpoints;
import com.cpt.payments.dto.Transaction;
import com.cpt.payments.pojo.PaymentResponse;
import com.cpt.payments.pojo.ProcessingServiceRequest;
import com.cpt.payments.pojo.TransactionReqRes;
import com.cpt.payments.service.PaymentService;
import com.cpt.payments.service.PaymentStatusService;
import com.cpt.payments.util.LogMessage;
import com.cpt.payments.util.TransactionMapper;

@RestController
@RequestMapping(Endpoints.PAYMENTS)
public class PaymentController {

	private static final Logger LOGGER = LogManager.getLogger(PaymentController.class);

	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private PaymentStatusService paymentStatusService;
	
	@Autowired
	private TransactionMapper transactionMapper;
	
	@PostMapping(value = Endpoints.STATUS_UPDATE)
	public ResponseEntity<TransactionReqRes> processPaymentStatus(@RequestBody TransactionReqRes transactionReqRes) {
		LogMessage.setLogMessagePrefix("/STATUS_UPDATE");
		LogMessage.log(LOGGER, " payment request is -> " + transactionReqRes);

		
		Transaction transaction = transactionMapper.toDTO(transactionReqRes);
		
		Transaction response = paymentStatusService.updatePaymentStatus(transaction);
        
		TransactionReqRes responseObject = transactionMapper.toResponseObject(response);
		return ResponseEntity.ok(responseObject);
	}

	@PostMapping(value = Endpoints.PROCESS_PAYMENT)
	public ResponseEntity<PaymentResponse> processPayment(
			@RequestBody ProcessingServiceRequest processingServiceRequest) {
		LogMessage.setLogMessagePrefix("/PROCESS_PAYMENT");
		LogMessage.log(LOGGER, " processingServiceRequest is -> " + processingServiceRequest);

		PaymentResponse response = paymentService.processPayment(processingServiceRequest);
		LogMessage.log(LOGGER, " processPayment response is -> " + processingServiceRequest);
		return ResponseEntity.ok(response);
	}

}
