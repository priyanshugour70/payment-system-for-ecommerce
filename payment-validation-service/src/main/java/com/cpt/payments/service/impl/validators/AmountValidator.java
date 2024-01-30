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
public class AmountValidator implements Validator {

	private static final Logger LOGGER = LogManager.getLogger(AmountValidator.class);

	private static final String AMOUNT_REGEX = "[0-9]{1,7}+([.][0-9]{2})";

	@Override
	public void doValidate(PaymentRequest paymentRequest) {
		LogMessage.log(LOGGER, " Validating amount request for -> " + paymentRequest);
		if (StringUtils.isBlank(paymentRequest.getPayment().getAmount())
				|| !paymentRequest.getPayment().getAmount().matches(AMOUNT_REGEX)) {
			LogMessage.log(LOGGER, " Amount field is missing or invalid");
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.AMOUNT_VALIDATION_FAILED.getErrorCode(),
					ErrorCodeEnum.AMOUNT_VALIDATION_FAILED.getErrorMessage());
		}

	}

}
