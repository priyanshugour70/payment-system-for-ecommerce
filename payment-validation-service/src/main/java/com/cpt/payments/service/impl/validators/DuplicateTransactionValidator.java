package com.cpt.payments.service.impl.validators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.constants.MerchantPaymentSaveResult;
import com.cpt.payments.dao.MerchantPaymentRequestDao;
import com.cpt.payments.dto.MerchantPaymentRequest;
import com.cpt.payments.exceptions.ValidationException;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.service.Validator;
import com.cpt.payments.util.LogMessage;
import com.google.gson.Gson;

@Component
public class DuplicateTransactionValidator implements Validator {
	
	private static final Logger LOGGER = LogManager.getLogger(DuplicateTransactionValidator.class);
	
	@Autowired
	private MerchantPaymentRequestDao merchantPaymentRequestDao;

	@Override
	public void doValidate(PaymentRequest paymentRequest) {
		String merchantTransactionId = paymentRequest.getPayment().getMerchantTransactionReference();
		
		MerchantPaymentRequest merchantPaymentRequest = merchantPaymentRequestDao.getMerchantPaymentRequest(merchantTransactionId);
		LogMessage.log(LOGGER, " merchantPaymentRequest is  -> "+merchantPaymentRequest);
		if(null != merchantPaymentRequest) {
			LogMessage.log(LOGGER, " Duplicate Transaction ");
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.DUPLICATE_TRANSACTION.getErrorCode(),
					ErrorCodeEnum.DUPLICATE_TRANSACTION.getErrorMessage());
		} else {
			Gson gson = new Gson();
			merchantPaymentRequest = MerchantPaymentRequest.builder()
					.merchantTransactionReference(merchantTransactionId)
					.transactionRequest(gson.toJson(paymentRequest))
					.build();
			LogMessage.log(LOGGER, " prepared merchantPaymentRequest is  -> "+merchantPaymentRequest);
			
			MerchantPaymentSaveResult response = 
					merchantPaymentRequestDao.insertMerchantPaymentRequest(merchantPaymentRequest);
			
			if(response == MerchantPaymentSaveResult.IS_DUPLICATE) {
				LogMessage.log(LOGGER, "Duplicate Transaction while insert");
				throw new ValidationException(HttpStatus.BAD_REQUEST,
						ErrorCodeEnum.DUPLICATE_TRANSACTION.getErrorCode(),
						ErrorCodeEnum.DUPLICATE_TRANSACTION.getErrorMessage());
			}
			
			if(response == MerchantPaymentSaveResult.IS_ERROR) {
				LogMessage.log(LOGGER, "Error saving MerchantPaymentRequest in DB");
				throw new ValidationException(HttpStatus.INTERNAL_SERVER_ERROR,
						ErrorCodeEnum.GENERIC_EXCEPTION.getErrorCode(),
						ErrorCodeEnum.GENERIC_EXCEPTION.getErrorMessage());
			}
		}
	}
}
