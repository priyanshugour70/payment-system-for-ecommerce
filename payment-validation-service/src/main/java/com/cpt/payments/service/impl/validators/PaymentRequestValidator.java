package com.cpt.payments.service.impl.validators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.exceptions.ValidationException;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.service.Validator;
import com.cpt.payments.util.LogMessage;

@Component
public class PaymentRequestValidator implements Validator {

	private static final Logger LOGGER = LogManager.getLogger(PaymentRequestValidator.class);
	
	@Override
	public void doValidate(PaymentRequest paymentRequest) {
		LogMessage.log(LOGGER, " Validating PAYMENT request for -> " + paymentRequest);
		if(null == paymentRequest) {
			LogMessage.log(LOGGER, " PAYMENT request is null -> " + paymentRequest);
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.PAYMENT_REQUEST_VALIDATION_FAILED.getErrorCode(),
					ErrorCodeEnum.PAYMENT_REQUEST_VALIDATION_FAILED.getErrorMessage());
		}

		if(null == paymentRequest.getPayment()) {
			LogMessage.log(LOGGER, " PAYMENT is null -> " + paymentRequest);
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.PAYMENT_VALIDATION_FAILED.getErrorCode(),
					ErrorCodeEnum.PAYMENT_VALIDATION_FAILED.getErrorMessage());
		}
		
		if(null == paymentRequest.getUser()) {
			LogMessage.log(LOGGER, " USER is null -> " + paymentRequest);
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.USER_VALIDATION_FAILED.getErrorCode(),
					ErrorCodeEnum.USER_VALIDATION_FAILED.getErrorMessage());
		}
	}

}
