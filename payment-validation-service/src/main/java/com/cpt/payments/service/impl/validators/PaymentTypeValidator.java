package com.cpt.payments.service.impl.validators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.constants.PaymentTypeEnum;
import com.cpt.payments.exceptions.ValidationException;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.service.Validator;
import com.cpt.payments.util.LogMessage;

import io.micrometer.core.instrument.util.StringUtils;

@Component
public class PaymentTypeValidator implements Validator {

private static final Logger LOGGER = LogManager.getLogger(PaymentTypeValidator.class);
	
	private static final String SALE = "SALE";
	@Override
	public void doValidate(PaymentRequest paymentRequest) {
		LogMessage.log(LOGGER, " Validating PAYMENT TYPE request for -> " + paymentRequest);
		if (StringUtils.isBlank(paymentRequest.getPayment().getPaymentType())
				|| !paymentRequest.getPayment().getPaymentType().equalsIgnoreCase(SALE)) {
			LogMessage.log(LOGGER, " paymentType feild is missing or not valid ");
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorCode(),
					ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorMessage());
		}
		
		if(null == PaymentTypeEnum.getPaymentType(paymentRequest.getPayment().getPaymentType())) {
			LogMessage.log(LOGGER, " paymentType feild is not valid ");
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorCode(),
					ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorMessage());
		}
	}

}
