package com.cpt.payments.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.pojo.PaymentError;
import com.cpt.payments.pojo.PaymentResponse;
import com.cpt.payments.util.LogMessage;

@ControllerAdvice
public class PaymentValidationServiceExceptionHandler {

	private static final Logger LOGGER = LogManager.getLogger(PaymentValidationServiceExceptionHandler.class);

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<PaymentError> handleValidationException(ValidationException ex) {
		LogMessage.log(LOGGER, " validation exception is -> " + ex.getErrorMessage());
		PaymentError paymentResponse = PaymentError.builder().errorCode(ex.getErrorCode())
				.errorMessage(ex.getErrorMessage()).build();
		LogMessage.log(LOGGER, " paymentResponse is -> " + paymentResponse);
		return new ResponseEntity<>(paymentResponse, ex.getHttpStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<PaymentError> handleGenericException(Exception ex) {
		LogMessage.log(LOGGER, " generic exception message is -> " + ex.getMessage());
		LogMessage.logException(LOGGER, ex);
		PaymentError paymentResponse = PaymentError.builder()
				.errorCode(ErrorCodeEnum.GENERIC_EXCEPTION.getErrorCode())
				.errorMessage(ErrorCodeEnum.GENERIC_EXCEPTION.getErrorMessage())
				.build();
		LogMessage.log(LOGGER, " paymentResponse is -> " + paymentResponse);
		return new ResponseEntity<>(paymentResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
