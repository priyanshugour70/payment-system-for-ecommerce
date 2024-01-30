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
public class EmailValidator implements Validator {

	private static final Logger LOGGER = LogManager.getLogger(EmailValidator.class);
	private static final String EMAIL_PATTERN = "(?i)[-a-zA-Z0-9+_][-a-zA-Z0-9+_.]*@[-a-zA-Z0-9][-a-zA-Z0-9.]*\\.[a-zA-Z]{2,30}";

	@Override
	public void doValidate(PaymentRequest paymentRequest) {
		LogMessage.log(LOGGER, " Validating email request for -> " + paymentRequest);
		String email = paymentRequest.getUser().getEmail();
		if (StringUtils.isBlank(email) || !email.matches(EMAIL_PATTERN)) {
			LogMessage.log(LOGGER, " email parameter is empty or not standard-> " + paymentRequest);
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.EMAIL_VALIDATION_FAILED.getErrorCode(),
					ErrorCodeEnum.EMAIL_VALIDATION_FAILED.getErrorMessage());
		}

	}

}
