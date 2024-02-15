package com.cpt.payments.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.pojo.response.error.ErrorData;
import com.cpt.payments.pojo.response.error.ErrorDetails;
import com.cpt.payments.pojo.response.error.ErrorWrapper;
import com.cpt.payments.pojo.response.error.TrustlyErrorResponse;
import com.cpt.payments.util.JsonUtils;
import com.cpt.payments.util.LogMessage;
import com.cpt.payments.util.SignatureCreator;

@ControllerAdvice
public class TrustlyCoreProviderExceptionHandler {

	private static final Logger LOGGER = LogManager.getLogger(TrustlyCoreProviderExceptionHandler.class);

	@Autowired
	private SignatureCreator creator;
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<TrustlyErrorResponse> handleValidationException(ValidationException ex) {
		LogMessage.log(LOGGER, " handleValidationException is -> " + ex.getErrorMessage());
		
		ErrorData errorData = ErrorData.builder()
				.code(ex.getErrorCode())
				.message(ex.getErrorMessage())
				.build();

		String serializedData = creator.serializeData(JsonUtils.toJsonNode(errorData));
		
		String signature = null;
		try {
			String plainText = ex.getMethod() + ex.getUuid() + serializedData;
 			signature = creator.generateSignature(plainText);
 			LogMessage.log(LOGGER, " Signature Generated||serializedData" + serializedData +
 					"|plainText:" + plainText + "|signature:" + signature);
		} catch (Exception e) {
			LogMessage.log(LOGGER, "Exception processing");
			LogMessage.logException(LOGGER, ex);
		}
		
		ErrorDetails errorDetails = ErrorDetails.builder()
				.uuid(ex.getUuid())
				.signature(signature)
				.method(ex.getMethod())
				.data(errorData)
				.build();
		ErrorWrapper errorWrapper = ErrorWrapper.builder()
				.name("JSONRPCError")
				.code(ex.getErrorCode())
				.message(ex.getErrorMessage())
				.error(errorDetails)
				.build();
		TrustlyErrorResponse errorRes = TrustlyErrorResponse.builder()
				.version("1.1")
				.error(errorWrapper)
				.build(); 
		
		LogMessage.log(LOGGER, " handleValidationException errorResponse is -> " + errorRes);
		return new ResponseEntity<>(errorRes, ex.getHttpStatus());
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<TrustlyErrorResponse> handleGenericException(Exception ex) {
		LogMessage.log(LOGGER, " generic exception message is -> " + ex.getMessage());
		LogMessage.logException(LOGGER, ex);
		
		ErrorData errorData = ErrorData.builder()
				.code(ErrorCodeEnum.GENERIC_EXCEPTION.getErrorCode())
				.message(ErrorCodeEnum.GENERIC_EXCEPTION.getErrorMessage())
				.build();
		ErrorDetails errorDetails = ErrorDetails.builder()
				.uuid(null)
				.signature("R9+hjuMqbsH0Ku ... S16VbzRsw==")
				.method("Deposit")
				.data(errorData)
				.build();
		ErrorWrapper errorWrapper = ErrorWrapper.builder()
				.name("JSONRPCError")
				.code(ErrorCodeEnum.GENERIC_EXCEPTION.getErrorCode())
				.message(ErrorCodeEnum.GENERIC_EXCEPTION.getErrorMessage())
				.error(errorDetails)
				.build();
		TrustlyErrorResponse errorRes = TrustlyErrorResponse.builder()
				.version("1.1")
				.error(errorWrapper)
				.build(); 
		
		LogMessage.log(LOGGER, " paymentResponse is -> " + errorRes);
		return new ResponseEntity<>(errorRes, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
