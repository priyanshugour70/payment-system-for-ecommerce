package com.cpt.payments.service.formatter.request;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
// import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cpt.payments.constants.Constants;
import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.exception.PaymentProcessException;
import com.cpt.payments.http.HttpRequest;
import com.cpt.payments.pojo.request.Attributes;
import com.cpt.payments.pojo.request.CoreTrustlyProvider;
import com.cpt.payments.pojo.request.Data;
import com.cpt.payments.pojo.request.Params;
import com.cpt.payments.pojo.request.TrustlyProviderRequest;
import com.cpt.payments.service.RequestFormatter;
import com.cpt.payments.util.JsonUtils;
import com.cpt.payments.util.LogMessage;
import com.cpt.payments.util.SignatureCreator;
import com.google.gson.Gson;

@Service
@Qualifier("InitiatePaymentRequestHandler")
public class InitiatePaymentRequestHandler implements RequestFormatter {
	private static final Logger LOGGER = LogManager.getLogger(InitiatePaymentRequestHandler.class);

	@Value("${trustly.initiate.payment.url}")
	private String initiatePaymentUrl;

	@Autowired
	private Gson gson;

	@Autowired
	private SignatureCreator signatureCreator;

	@Override
	public HttpRequest prepareRequest(TrustlyProviderRequest trustlyProviderRequest) {
		String initiatePaymentRequestPayload = prepareRequestPayload(trustlyProviderRequest);
		LogMessage.log(LOGGER, " preparing initiate payment request payload completed with values :: "
				+ initiatePaymentRequestPayload);
		HttpRequest httpRequest = HttpRequest.builder().httpMethod(HttpMethod.POST)
				.request(initiatePaymentRequestPayload).url(initiatePaymentUrl).build();
		LogMessage.log(LOGGER, " preparing initiate payment request completed.");
		return httpRequest;
	}


	private String prepareRequestPayload(TrustlyProviderRequest trustlyProviderRequest) {
		LogMessage.log(LOGGER, " started preparing initiate payment request");

		Attributes attributes = Attributes.builder().amount(trustlyProviderRequest.getAmount()).country("LT")
				.currency(trustlyProviderRequest.getCurrency()).email(trustlyProviderRequest.getEmail())
				.failURL("https://somedomain.com/failure/trustly/" + trustlyProviderRequest.getTransactionReference())
				.firstname(trustlyProviderRequest.getFirstName()).lastname(trustlyProviderRequest.getLastName())
				.locale("en")
				.successURL(
						"https://somedomain.com/success/trustly/" + trustlyProviderRequest.getTransactionReference())
				.build();

		Data data = Data.builder()
				.attributes(attributes)
				.endUserID(trustlyProviderRequest.getTransactionReference())
				.messageID(trustlyProviderRequest.getTransactionReference())
				.notificationURL(
						"https://somedomain.com/trustly/notify" + trustlyProviderRequest.getTransactionReference())
				.password("CTPpassword")
				.username("CTPuser").build();

		String signature = null;
		try {

			String method = Constants.METHOD_DEPOSIT;
			String uuid = trustlyProviderRequest.getTransactionReference();
			String jsonDataAsSerializedString = signatureCreator.serializeData(JsonUtils.toJsonNode(data));

			String plainText = method + uuid + jsonDataAsSerializedString;
			LogMessage.debug(LOGGER, " Generate signature for|plainText:" + plainText);
			
			signature = signatureCreator.generateSignature(plainText);
			LogMessage.debug(LOGGER, " Signature generated|signature:" + signature);
		} catch (Exception e) {
			LogMessage.log(LOGGER, " exception while creating signature ");
			throw new PaymentProcessException(HttpStatus.INTERNAL_SERVER_ERROR,
					ErrorCodeEnum.FAILED_TO_CREATE_SIGNATURE.getErrorCode(),
					ErrorCodeEnum.FAILED_TO_CREATE_SIGNATURE.getErrorMessage());
		}

		Params params = Params.builder()
				.data(data)
				.uuid(trustlyProviderRequest.getTransactionReference())
				.signature(signature)
				.build();

		CoreTrustlyProvider coreTrustlyProvider = CoreTrustlyProvider.builder()
				.method(Constants.METHOD_DEPOSIT)
				.params(params)
				.version("1.1").build();

		LogMessage.log(LOGGER, " coreTrustlyProvider request is -> " + coreTrustlyProvider);
		return gson.toJson(coreTrustlyProvider);
	}

}
