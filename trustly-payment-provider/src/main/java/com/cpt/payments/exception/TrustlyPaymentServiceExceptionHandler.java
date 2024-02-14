package com.cpt.payments.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.pojo.ErrorResponse;
import com.cpt.payments.util.LogMessage;

@ControllerAdvice
public class TrustlyPaymentServiceExceptionHandler {

	private static final Logger LOGGER = LogManager.getLogger(TrustlyPaymentServiceExceptionHandler.class);

	@ExceptionHandler(PaymentProcessException.class)
	public ResponseEntity<ErrorResponse> handlePaymentProcessException(PaymentProcessException ex) {
		LogMessage.log(LOGGER, " Payment Process exception message is -> " + ex.getMessage());
		ErrorResponse paymentResponse = ErrorResponse.builder()
				.errorCode(ex.getErrorCode())
				.errorMessage(ex.getErrorMessage())
				.tpProviderError(ex.isTpProviderError())
				.build();
		LogMessage.log(LOGGER, " paymentResponse with error details is -> " + paymentResponse);
		return new ResponseEntity<>(paymentResponse, ex.getHttpStatus());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
		LogMessage.log(LOGGER, " generic exception message is -> " + ex.getMessage());
		LogMessage.logException(LOGGER, ex);
		ErrorResponse paymentResponse = ErrorResponse.builder()
				.errorCode(ErrorCodeEnum.GENERIC_EXCEPTION.getErrorCode())
				.errorMessage(ErrorCodeEnum.GENERIC_EXCEPTION.getErrorMessage()).build();
		LogMessage.log(LOGGER, " paymentResponse with error details is -> " + paymentResponse);
		return new ResponseEntity<>(paymentResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
