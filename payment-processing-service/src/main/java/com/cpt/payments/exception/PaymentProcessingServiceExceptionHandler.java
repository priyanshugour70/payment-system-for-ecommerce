package com.cpt.payments.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.pojo.ErrorResponse;
import com.cpt.payments.pojo.PaymentResponse;
import com.cpt.payments.util.LogMessage;

@ControllerAdvice
public class PaymentProcessingServiceExceptionHandler {

	private static final Logger LOGGER = LogManager.getLogger(PaymentProcessingServiceExceptionHandler.class);

	@ExceptionHandler(PaymentProcessingException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(PaymentProcessingException ex) {
		LogMessage.log(LOGGER, " validation exception is -> " + ex.getErrorMessage());
		
		ErrorResponse errorResponse = ErrorResponse.builder()
				.errorCode(ex.getErrorCode())
				.errorMessage(ex.getErrorMessage())
				.build();
		
		LogMessage.log(LOGGER, " paymentResponse is -> " + errorResponse);
		return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
		LogMessage.log(LOGGER, " generic exception message is -> " + ex.getMessage());
		LogMessage.logException(LOGGER, ex);
		
		ErrorResponse errorResponse = ErrorResponse.builder()
				.errorCode(ErrorCodeEnum.GENERIC_EXCEPTION.getErrorCode())
				.errorMessage(ErrorCodeEnum.GENERIC_EXCEPTION.getErrorMessage()).build();
		
		LogMessage.log(LOGGER, " paymentResponse is -> " + errorResponse);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
