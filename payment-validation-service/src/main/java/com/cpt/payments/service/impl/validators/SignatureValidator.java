package com.cpt.payments.service.impl.validators;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.exceptions.ValidationException;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.service.Validator;
import com.cpt.payments.util.HmacSha256;
import com.cpt.payments.util.LogMessage;
import com.google.gson.Gson;

@Component
public class SignatureValidator implements Validator {

	private static final Logger LOGGER = LogManager.getLogger(SignatureValidator.class);

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Value("${payment.signatureKey}")
	private String signatureKey;

	@Autowired
	private HmacSha256 hmacSha256;

	@Override
	public void doValidate(PaymentRequest paymentRequest) {
		try {
			Gson gson = new Gson();
			LogMessage.log(LOGGER, " Validating signature request for -> " + paymentRequest);
			String signature = httpServletRequest.getHeader("signature");
			if (null == signature) {
				LogMessage.log(LOGGER, " signature not found");
				throw new ValidationException(HttpStatus.UNAUTHORIZED, ErrorCodeEnum.SIGNATURE_NOT_FOUND.getErrorCode(),
						ErrorCodeEnum.SIGNATURE_NOT_FOUND.getErrorMessage());
			}
			byte[] signatureKeyBytes = signatureKey.getBytes();

			String messageDigest = hmacSha256.generateHmac256(gson.toJson(paymentRequest), signatureKeyBytes);
			if (!messageDigest.equals(signature)) {
				LogMessage.log(LOGGER, "signature is not matched. payment request is altered");
				LogMessage.log(LOGGER, "messageDigest ::" + messageDigest + " , signature :: " + signature);

				throw new ValidationException(HttpStatus.UNAUTHORIZED, ErrorCodeEnum.SIGNATURE_ALTERED.getErrorCode(),
						ErrorCodeEnum.SIGNATURE_ALTERED.getErrorMessage());
			}
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			LogMessage.log(LOGGER, "Error Occurred in generating HmacSHA256 string :: " + e.getMessage());
			throw new ValidationException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodeEnum.EXCEPTION_IN_SIGNATURE_CALCULATION.getErrorCode()
					, ErrorCodeEnum.EXCEPTION_IN_SIGNATURE_CALCULATION.getErrorMessage());
		}

	}

}
