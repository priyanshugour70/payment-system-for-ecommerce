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

import io.micrometer.core.instrument.util.StringUtils;

@Component
public class PhoneNumberValidator implements Validator {

	private static final Logger LOGGER = LogManager.getLogger(PhoneNumberValidator.class);

	private static final String PHONE_PATTERN = "^[+]{1}[0-9]{1,15}";

	@Override
	public void doValidate(PaymentRequest paymentRequest) {
		LogMessage.log(LOGGER, " Validating phone number request for -> " + paymentRequest);
		if (StringUtils.isBlank(paymentRequest.getUser().getPhoneNumber())
				|| !paymentRequest.getUser().getPhoneNumber().matches(PHONE_PATTERN)) {
			LogMessage.log(LOGGER, " phone number feild is missing or not valid ");
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.PHONE_NUMBER_VALIDATION_FAILED.getErrorCode(),
					ErrorCodeEnum.PHONE_NUMBER_VALIDATION_FAILED.getErrorMessage());
		}
	}

}
