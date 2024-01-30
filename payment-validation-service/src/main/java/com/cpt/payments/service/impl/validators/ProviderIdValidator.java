package com.cpt.payments.service.impl.validators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.constants.ProviderEnum;
import com.cpt.payments.exceptions.ValidationException;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.service.Validator;
import com.cpt.payments.util.LogMessage;

import io.micrometer.core.instrument.util.StringUtils;

@Component
public class ProviderIdValidator implements Validator {

private static final Logger LOGGER = LogManager.getLogger(ProviderIdValidator.class);
	
	private static final String TRUSTLY = "TRUSTLY";
	@Override
	public void doValidate(PaymentRequest paymentRequest) {
		LogMessage.log(LOGGER, " Validating PROVIDER ID request for -> " + paymentRequest);
		if (StringUtils.isBlank(paymentRequest.getPayment().getProviderId())
				|| !paymentRequest.getPayment().getProviderId().equalsIgnoreCase(TRUSTLY)) {
			LogMessage.log(LOGGER, " PROVIDER ID feild is missing or not valid ");
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorCode(),
					ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorMessage());
		}
		
		if(null == ProviderEnum.getProviderEnum(paymentRequest.getPayment().getProviderId())) {
			LogMessage.log(LOGGER, " PROVIDER ID feild is not valid ");
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorCode(),
					ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorMessage());
		}
	}

}
