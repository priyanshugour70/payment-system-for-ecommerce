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
public class MerchantTransactionIDValidator implements Validator {

	private static final Logger LOGGER = LogManager.getLogger(MerchantTransactionIDValidator.class);

	private static final String MERCHANT_TXN_CODE_PATTERN = "^[A-Za-z0-9\\-_]+$";

	@Override
	public void doValidate(PaymentRequest paymentRequest) {
		LogMessage.log(LOGGER, " Validating merchantTxnReference request for -> " + paymentRequest);
		if (StringUtils.isBlank(paymentRequest.getPayment().getMerchantTransactionReference())
				|| !paymentRequest.getPayment().getMerchantTransactionReference().matches(MERCHANT_TXN_CODE_PATTERN)) {
			LogMessage.log(LOGGER, " merchantTxnReference feild is missing or not valid ");
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.MERCHANT_TRANSACTION_REFERENCE_VALIDATION_FAILED.getErrorCode(),
					ErrorCodeEnum.MERCHANT_TRANSACTION_REFERENCE_VALIDATION_FAILED.getErrorMessage());
		}

	}
}
