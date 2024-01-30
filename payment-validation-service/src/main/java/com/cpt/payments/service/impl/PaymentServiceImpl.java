package com.cpt.payments.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.constants.PaymentMethodEnum;
import com.cpt.payments.constants.PaymentTypeEnum;
import com.cpt.payments.constants.ProviderEnum;
import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.constants.ValidatorEnum;
import com.cpt.payments.exceptions.ValidationException;
import com.cpt.payments.http.HttpRequest;
import com.cpt.payments.http.HttpRestTemplateEngine;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.pojo.PaymentResponse;
import com.cpt.payments.pojo.ProcessingServiceRequest;
import com.cpt.payments.pojo.TransactionReqRes;
import com.cpt.payments.pojo.processing.response.ErrorResponse;
import com.cpt.payments.service.PaymentService;
import com.cpt.payments.service.Supplier;
import com.cpt.payments.service.UserService;
import com.cpt.payments.service.Validator;
import com.cpt.payments.util.LogMessage;
import com.google.gson.Gson;

@Service
public class PaymentServiceImpl implements PaymentService {

	private static final Logger LOGGER = LogManager.getLogger(PaymentServiceImpl.class);

	@Autowired
	private ApplicationContext context;

	@Value("${payment.validators}")
	private String validators;
	
	@Value("${payment.processing.service.initiate.payment}")
	private String initiatePaymentUrl;
	
	@Value("${payment.processing.service.process.payment}")
	private String processPaymentUrl;

	@Autowired
	private UserService userService;

	@Autowired
	private HttpRestTemplateEngine httpRestTemplateEngine;

	@Override
	public PaymentResponse validateAndInitiatePayment(PaymentRequest paymentRequest) {
		LogMessage.log(LOGGER, " validators are -> " + validators);

		List<String> validatorList = Stream.of(validators.split(",")).collect(Collectors.toList());

		validatorList.forEach(validator -> {
			ValidatorEnum validatorEnum = ValidatorEnum.getEnumByString(validator);
			
			Supplier<? extends Validator> validatorSupplier = () -> context.getBean(validatorEnum.getValidatorClass());
			validatorSupplier.get().doValidate(paymentRequest);
		});

		Long userId = userService.createUser(paymentRequest);

		TransactionReqRes transaction = initiateTransaction(userId, paymentRequest);

		PaymentResponse paymentResponse = processTransaction(transaction, paymentRequest);
		return paymentResponse;
	}

	private PaymentResponse processTransaction(TransactionReqRes transaction, PaymentRequest paymentRequest) {
		ProcessingServiceRequest processingServiceRequest = ProcessingServiceRequest.builder()
				.transactionId(transaction.getId())
				.firstName(paymentRequest.getUser().getFirstName())
				.lastName(paymentRequest.getUser().getLastName())
				.email(paymentRequest.getUser().getEmail())
				.build();
		
		Gson gson = new Gson();
		
		HttpRequest httpRequest = HttpRequest.builder().url(processPaymentUrl).httpMethod(HttpMethod.POST)
				.request(gson.toJson(processingServiceRequest)).build();
		
		
		ResponseEntity<String> response = httpRestTemplateEngine.execute(httpRequest);
		if(null == response || null == response.getBody()) {
			LogMessage.log(LOGGER, " payment processing failed -> " + transaction);
			throw new ValidationException(HttpStatus.INTERNAL_SERVER_ERROR,
					ErrorCodeEnum.GENERIC_EXCEPTION.getErrorCode(),
					ErrorCodeEnum.GENERIC_EXCEPTION.getErrorMessage());
		}
		
		if(200 != response.getStatusCodeValue()) {
			LogMessage.debug(LOGGER, " payment processing failed||responseStatusCode:" + response.getStatusCodeValue());
			
			ErrorResponse errorResponse = gson.fromJson(response.getBody(), ErrorResponse.class);
			LogMessage.log(LOGGER, " payment processing failed||responseStatusCode:" + response.getStatusCodeValue() +
					"|paymentResponse:" + errorResponse);
				
			throw new ValidationException(HttpStatus.BAD_GATEWAY,
					errorResponse.getErrorCode(),
					errorResponse.getErrorMessage());
		}
		
		//Success
		PaymentResponse paymentResponse = gson.fromJson(response.getBody(), PaymentResponse.class);
		LogMessage.log(LOGGER, " payment response is -> " + paymentResponse);
		return paymentResponse;
	}

	private TransactionReqRes initiateTransaction(Long userId, PaymentRequest paymentRequest) {

		TransactionReqRes transaction = TransactionReqRes.builder()
				.amount(Double.parseDouble(paymentRequest.getPayment().getAmount()))
				.creditorAccount(paymentRequest.getPayment().getCreditorAccount())
				.debitorAccount(paymentRequest.getPayment().getDebitorAccount())
				.currency(paymentRequest.getPayment().getCurrency())
				.paymentMethodId(PaymentMethodEnum.getPaymentMethod(paymentRequest.getPayment().getPaymentMethod())
						.getPaymentMethodId())
				.paymentTypeId(
						PaymentTypeEnum.getPaymentType(paymentRequest.getPayment().getPaymentType()).getPaymentTypeId())
				.providerId(ProviderEnum.getProviderEnum(paymentRequest.getPayment().getProviderId()).getProviderId())
				.txnReference(UUID.randomUUID().toString()).txnStatusId(TransactionStatusEnum.CREATED.getId())
				.userId(userId)
				.merchantTransactionReference(paymentRequest.getPayment().getMerchantTransactionReference())
				.build();

		LogMessage.log(LOGGER, " payment object is -> " + transaction);
		
		Gson gson = new Gson();

		HttpRequest httpRequest = HttpRequest.builder().url(initiatePaymentUrl).httpMethod(HttpMethod.POST)
				.request(gson.toJson(transaction)).build();

		ResponseEntity<String> response = httpRestTemplateEngine.execute(httpRequest);
		if(null == response || null == response.getBody()) {
			LogMessage.log(LOGGER, " payment object initiated failed -> " + transaction);
			throw new ValidationException(HttpStatus.INTERNAL_SERVER_ERROR,
					ErrorCodeEnum.FAILED_TO_CREATE_TRANSACTION.getErrorCode(),
					ErrorCodeEnum.FAILED_TO_CREATE_TRANSACTION.getErrorMessage());
		}
		
		if(200 != response.getStatusCodeValue()) {
			LogMessage.debug(LOGGER, " payment processing failed||responseStatusCode:" + response.getStatusCodeValue());
			
			ErrorResponse errorResponse = gson.fromJson(response.getBody(), ErrorResponse.class);
			LogMessage.log(LOGGER, " payment processing failed||responseStatusCode:" + response.getStatusCodeValue() +
					"|paymentResponse:" + errorResponse);
				
			throw new ValidationException(HttpStatus.BAD_GATEWAY,
					errorResponse.getErrorCode(),
					errorResponse.getErrorMessage());
		}
		
		transaction = gson.fromJson(response.getBody(), TransactionReqRes.class);
		LogMessage.log(LOGGER, " payment id is -> " + transaction.getId());
		return transaction;
		
	}

}
