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
public class FirstNameValidator implements Validator {
	private static final Logger LOGGER = LogManager.getLogger(FirstNameValidator.class);

	private static final String ALLOWED_CHARS = "^[\\p{L}0-9-?:(),.'+ ]*$";

	@Override
	public void doValidate(PaymentRequest paymentRequest) {
		LogMessage.log(LOGGER, " Validating firstName request for -> " + paymentRequest);
		if (StringUtils.isBlank(paymentRequest.getUser().getFirstName())
				|| !paymentRequest.getUser().getFirstName().matches(ALLOWED_CHARS)) {
			LogMessage.log(LOGGER, " firstName feild is missing or not valid ");
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.FIRSTNAME_VALIDATION_FAILED.getErrorCode(),
					ErrorCodeEnum.FIRSTNAME_VALIDATION_FAILED.getErrorMessage());
		}

	}

}
