package com.cpt.payments.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.exception.PaymentProcessException;
import com.cpt.payments.http.HttpRequest;
import com.cpt.payments.http.HttpRestTemplateEngine;
import com.cpt.payments.pojo.request.TrustlyProviderRequest;
import com.cpt.payments.pojo.response.TrustlyProviderResponse;
import com.cpt.payments.service.PaymentService;
import com.cpt.payments.service.formatter.request.InitiatePaymentRequestHandler;
import com.cpt.payments.service.formatter.response.InitiatePaymentResponseHandler;
import com.cpt.payments.service.helper.PaymentServiceHelper;
import com.cpt.payments.util.LogMessage;

@Service
public class PaymentServiceImpl implements PaymentService {
	private static final Logger LOGGER = LogManager.getLogger(PaymentServiceImpl.class);
	
	@Autowired
	private InitiatePaymentRequestHandler initiatePaymentRequestHandler;
	
	@Autowired
	private InitiatePaymentResponseHandler initiatePaymentResponseHandler;
	
	@Autowired
	private HttpRestTemplateEngine httpRestTemplateEngine;
	
	@Autowired
	private PaymentServiceHelper paymentServiceHelper;
	
	@Override
	public TrustlyProviderResponse initiatePayment(TrustlyProviderRequest trustlyProviderRequest) {
		HttpRequest httpRequest = initiatePaymentRequestHandler.prepareRequest(trustlyProviderRequest);
		ResponseEntity<String> response = httpRestTemplateEngine.execute(httpRequest);
		if (null == response) {
			LogMessage.log(LOGGER, " failed to connect to trustly provider -> " + response);
			throw new PaymentProcessException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.FAILED_TO_CONNECT_TO_TRUSTLY.getErrorCode(),
					ErrorCodeEnum.FAILED_TO_CONNECT_TO_TRUSTLY.getErrorMessage());
		}
		paymentServiceHelper.saveProviderRequestResponse(trustlyProviderRequest.getTransactionReference(), httpRequest, response);
		
		return initiatePaymentResponseHandler.processResponse(response);
	}

}
