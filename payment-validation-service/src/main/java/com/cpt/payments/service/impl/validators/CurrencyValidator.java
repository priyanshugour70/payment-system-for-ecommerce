package com.cpt.payments.service.impl.validators;

import org.apache.commons.lang3.StringUtils;
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
public class CurrencyValidator implements Validator {

	private static final Logger LOGGER = LogManager.getLogger(CurrencyValidator.class);
	private static final String CURRENCY_REGEX = "[A-Z]{3}";

	@Override
	public void doValidate(PaymentRequest paymentRequest) {
		LogMessage.log(LOGGER, " Validating CURRENCY request for -> " + paymentRequest);
		String currency = paymentRequest.getPayment().getCurrency();
		if (StringUtils.isBlank(currency) || !StringUtils.isAlpha(currency) || StringUtils.length(currency.trim()) != 3
				|| !currency.matches(CURRENCY_REGEX)) {
			LogMessage.log(LOGGER, " currency parameter is empty or not ISO-3 standard-> " + paymentRequest);
			throw new ValidationException(HttpStatus.BAD_REQUEST, ErrorCodeEnum.CURRENCY_VALIDATION_FAILED.getErrorCode(),
					ErrorCodeEnum.CURRENCY_VALIDATION_FAILED.getErrorMessage());
		}

	}

}
