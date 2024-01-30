package com.cpt.payments.adapter;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.dao.TransactionDao;
import com.cpt.payments.dto.Transaction;
import com.cpt.payments.exception.PaymentProcessingException;
import com.cpt.payments.pojo.TrustlyNotificationRequest;
import com.cpt.payments.service.PaymentStatusService;
import com.cpt.payments.util.LogMessage;
import com.cpt.payments.util.SHA256RSASignatureVerifier;
import com.google.gson.Gson;

@Component
public class TrustlyNotificationAdapter {

	private static final String FAILED = "Failed";

	private static final String SUCCESS = "Success";

	private static final Logger LOGGER = LogManager.getLogger(TrustlyNotificationAdapter.class);
	
	@Autowired
	private PaymentStatusService paymentStatusServiceImpl;
	
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	@Autowired
	private SHA256RSASignatureVerifier sha256RSASignatureVerifier;
	
	@Autowired
	private TransactionDao transactionDaoImpl;
	
	private static final String PUBLIC_KEY_PATH = "./src/main/java/com/cpt/payments/util/public_trustly.pem";

	public void processNotification(TrustlyNotificationRequest request) {
		
		LogMessage.log(LOGGER, " Validating signature request for -> " + request);
		String signature = httpServletRequest.getHeader("signature");
		if (null == signature) {
			LogMessage.log(LOGGER, " signature not found");
			throw new PaymentProcessingException(HttpStatus.UNAUTHORIZED, ErrorCodeEnum.SIGNATURE_NOT_FOUND.getErrorCode(),
					ErrorCodeEnum.SIGNATURE_NOT_FOUND.getErrorMessage());
		}
		
		Gson gson = new Gson();
		try {
			if(!sha256RSASignatureVerifier.verifySignature(signature, gson.toJson(request), PUBLIC_KEY_PATH)) {
				LogMessage.log(LOGGER, ":: Signature is Invalid ::");
	            throw new PaymentProcessingException(HttpStatus.BAD_REQUEST, ErrorCodeEnum.INVALID_SIGNATURE.getErrorCode(),
						ErrorCodeEnum.INVALID_SIGNATURE.getErrorMessage());
			}
		} catch(Exception e) {
			LogMessage.log(LOGGER, " Exception while validating signature ");
			throw new PaymentProcessingException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodeEnum.SIGNATURE_VERIFICATION_FAILED.getErrorCode(),
					ErrorCodeEnum.SIGNATURE_VERIFICATION_FAILED.getErrorMessage());
		}
		
		LogMessage.log(LOGGER, " signature verified -> " + request);
		
		Transaction transaction = transactionDaoImpl.getTransactionByProviderReference(request.getPaymentId());
		if (null == transaction) {
			LogMessage.log(LOGGER, "transaction not found -> " + transaction);
			throw new PaymentProcessingException(HttpStatus.BAD_REQUEST, ErrorCodeEnum.PAYMENT_NOT_FOUND.getErrorCode(),
					ErrorCodeEnum.PAYMENT_NOT_FOUND.getErrorMessage());
		}
		if(SUCCESS.equalsIgnoreCase(request.getStatus())) {
			transaction.setTxnStatusId(TransactionStatusEnum.APPROVED.getId());
		} else if(FAILED.equalsIgnoreCase(request.getStatus())){
			transaction.setTxnStatusId(TransactionStatusEnum.FAILED.getId());
		}
		
		transaction = paymentStatusServiceImpl.updatePaymentStatus(transaction);
		transaction.setProviderCode(request.getCode());
		transaction.setProviderMessage(request.getMessage());
		transactionDaoImpl.updateProviderCodeAndMessage(transaction);
	}

}
