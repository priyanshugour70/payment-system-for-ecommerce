package com.cpt.payments.service.Impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.dao.TransactionDao;
import com.cpt.payments.dto.Transaction;
import com.cpt.payments.exception.PaymentProcessingException;
import com.cpt.payments.pojo.PaymentResponse;
import com.cpt.payments.pojo.ProcessingServiceRequest;
import com.cpt.payments.service.PaymentService;
import com.cpt.payments.service.ProviderHandler;
import com.cpt.payments.service.factory.ProviderHandlerFactory;
import com.cpt.payments.util.LogMessage;

@Component
public class PaymentServiceImpl implements PaymentService {

	private static final Logger LOGGER = LogManager.getLogger(PaymentServiceImpl.class);

	@Autowired
	private TransactionDao transactionDaoImpl;

	@Autowired
	private ProviderHandlerFactory providerHandlerFactory;

	@Override
	public PaymentResponse processPayment(ProcessingServiceRequest processingServiceRequest) {
		Transaction transaction = transactionDaoImpl.getTransactionById(processingServiceRequest.getTransactionId());
		LogMessage.log(LOGGER, "transaction is -> " + transaction);
		if (null == transaction) {
			LogMessage.log(LOGGER, "transaction not found -> " + transaction);
			throw new PaymentProcessingException(HttpStatus.BAD_REQUEST, ErrorCodeEnum.PAYMENT_NOT_FOUND.getErrorCode(),
					ErrorCodeEnum.PAYMENT_NOT_FOUND.getErrorMessage());
		}
		ProviderHandler providerHandler = providerHandlerFactory.getProviderHandler(transaction.getProviderId());
		LogMessage.log(LOGGER, "providerHandler is -> " + providerHandler);
		if (null == providerHandler) {
			LogMessage.log(LOGGER, "provider not found -> " + transaction.getProviderId());
			throw new PaymentProcessingException(HttpStatus.BAD_REQUEST, ErrorCodeEnum.PROVIDER_NOT_FOUND.getErrorCode(),
					ErrorCodeEnum.PROVIDER_NOT_FOUND.getErrorMessage());
		}

		PaymentResponse paymentResponse = providerHandler.processPayment(transaction, processingServiceRequest);
		LogMessage.log(LOGGER, "paymentResponse is -> " + paymentResponse);
		return paymentResponse;
	}
}
