package com.cpt.payments.service.impl.validators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.constants.PaymentMethodEnum;
import com.cpt.payments.exceptions.ValidationException;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.service.Validator;
import com.cpt.payments.util.LogMessage;

import io.micrometer.core.instrument.util.StringUtils;

@Component
public class PaymentMethodValidator implements Validator {

	private static final Logger LOGGER = LogManager.getLogger(PaymentMethodValidator.class);
	
	private static final String APM = "APM";
	@Override
	public void doValidate(PaymentRequest paymentRequest) {
		LogMessage.log(LOGGER, " Validating PAYMENT METHOD request for -> " + paymentRequest);
		if (StringUtils.isBlank(paymentRequest.getPayment().getPaymentMethod())
				|| !paymentRequest.getPayment().getPaymentMethod().equalsIgnoreCase(APM)) {
			LogMessage.log(LOGGER, " paymentMethod feild is missing or not valid ");
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.PAYMENT_METHOD_VALIDATION_FAILED.getErrorCode(),
					ErrorCodeEnum.PAYMENT_METHOD_VALIDATION_FAILED.getErrorMessage());
		}
		
		if(null == PaymentMethodEnum.getPaymentMethod(paymentRequest.getPayment().getPaymentMethod())){
			LogMessage.log(LOGGER, " paymentMethod feild is not valid ");
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.PAYMENT_METHOD_VALIDATION_FAILED.getErrorCode(),
					ErrorCodeEnum.PAYMENT_METHOD_VALIDATION_FAILED.getErrorMessage());
		}
	}

}
