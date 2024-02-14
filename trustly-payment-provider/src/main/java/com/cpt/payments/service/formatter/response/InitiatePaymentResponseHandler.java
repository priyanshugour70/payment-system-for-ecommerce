package com.cpt.payments.service.formatter.response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cpt.payments.constants.Constants;
import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.exception.PaymentProcessException;
import com.cpt.payments.pojo.response.TrustlyCoreResponse;
import com.cpt.payments.pojo.response.TrustlyProviderResponse;
import com.cpt.payments.pojo.response.error.ErrorDetails;
import com.cpt.payments.service.ResponseFormatter;
import com.cpt.payments.service.helper.PaymentServiceHelper;
import com.cpt.payments.util.LogMessage;
import com.google.gson.Gson;

@Service
@Qualifier("InitiatePaymentResponseHandler")
public class InitiatePaymentResponseHandler implements ResponseFormatter{
	private static final Logger LOGGER = LogManager.getLogger(InitiatePaymentResponseHandler.class);

	@Autowired
	private Gson gson;

	@Autowired
	PaymentServiceHelper serviceHelper;

	@Override
	public TrustlyProviderResponse processResponse(ResponseEntity<String> response) {
		LogMessage.log(LOGGER, "status received from trustly while initiating payment :: " + response.getStatusCodeValue());
		if (null == response.getBody()) {
			LogMessage.log(LOGGER, " failed to initiate payment at trustly provider -> " + response);
			throw new PaymentProcessException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.FAILED_TO_INITIATE_PAYMENT_AT_TRUSTLY.getErrorCode(),
					ErrorCodeEnum.FAILED_TO_INITIATE_PAYMENT_AT_TRUSTLY.getErrorMessage());
		}

		if(200 != response.getStatusCodeValue()) {
			handleNon200Response(response);
		}

		TrustlyCoreResponse providerResponse = gson.fromJson(response.getBody(), TrustlyCoreResponse.class);

		LogMessage.log(LOGGER, "providerResponse:" + providerResponse);

		boolean isSigValid = serviceHelper.isResponseSignatureValid(
				providerResponse.getResult().getSignature(), 
				providerResponse.getResult().getData(), 
				providerResponse.getResult().getUuid(), 
				Constants.METHOD_DEPOSIT);

		if(!isSigValid) {
			LogMessage.log(LOGGER, " INVALID signature in Trustly response");
			throw new PaymentProcessException(HttpStatus.UNAUTHORIZED, 
					ErrorCodeEnum.FAILED_TO_PROCESS_TRUSTLY_SIGNATURE.getErrorCode(),
					ErrorCodeEnum.FAILED_TO_PROCESS_TRUSTLY_SIGNATURE.getErrorMessage());
		}

		LogMessage.debug(LOGGER, " Response SIGNATURE VALID, continuing repsonse processing");

		TrustlyProviderResponse trustlyProviderResponse = TrustlyProviderResponse.builder()
				.paymentId(providerResponse.getResult().getData().getOrderid())
				.redirectUrl(providerResponse.getResult().getData().getUrl())
				.build();
		LogMessage.log(LOGGER, "response received from trustly while initiating payment :: " + providerResponse);
		return trustlyProviderResponse;
	}

	private void handleNon200Response(ResponseEntity<String> response) {
		TrustlyCoreResponse providerResponse = gson.fromJson(response.getBody(), TrustlyCoreResponse.class);
		if(providerResponse.getError() != null) {// Received Trustly ErrorResponse
			LogMessage.log(LOGGER, " failed to initiate payment at trustly provider -> " + response);

			//Check Trustly Error Signature
			ErrorDetails errorDetails = providerResponse.getError().getError();
			boolean isSigValid = serviceHelper.isResponseSignatureValid(
					errorDetails.getSignature(), 
					errorDetails.getData(), 
					errorDetails.getUuid(), 
					Constants.METHOD_DEPOSIT);

			if(!isSigValid) {
				LogMessage.log(LOGGER, " INVALID signature in Trustly response");
				throw new PaymentProcessException(HttpStatus.UNAUTHORIZED, 
						ErrorCodeEnum.FAILED_TO_PROCESS_TRUSTLY_SIGNATURE.getErrorCode(),
						ErrorCodeEnum.FAILED_TO_PROCESS_TRUSTLY_SIGNATURE.getErrorMessage());
			}

			// Return Trustly error to Payment Processing service.
			LogMessage.log(LOGGER, " Throwing error with Trustly Error Response");
			throw new PaymentProcessException(HttpStatus.BAD_REQUEST,
					errorDetails.getData().getCode(),
					errorDetails.getData().getMessage(),
					true);
		} else {
			LogMessage.log(LOGGER, " HTTP Status Code !200, & not TRUSTLY Valid Response||response:" + response);
			throw new PaymentProcessException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.FAILED_TO_INITIATE_PAYMENT_AT_TRUSTLY.getErrorCode(),
					ErrorCodeEnum.FAILED_TO_INITIATE_PAYMENT_AT_TRUSTLY.getErrorMessage());
		}
	}



}

