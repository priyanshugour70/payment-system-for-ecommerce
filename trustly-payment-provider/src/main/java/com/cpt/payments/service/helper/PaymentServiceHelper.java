package com.cpt.payments.service.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.cpt.payments.dao.ProviderRequestResponseDao;
import com.cpt.payments.dto.ProviderRequestResponseDto;
import com.cpt.payments.http.HttpRequest;
// import com.cpt.payments.pojo.response.TrustlyCoreResponse;
import com.cpt.payments.util.JsonUtils;
import com.cpt.payments.util.LogMessage;
import com.cpt.payments.util.SHA256RSASignatureVerifier;
import com.cpt.payments.util.SignatureCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;

@Component
public class PaymentServiceHelper {

	private static final Logger LOGGER = LogManager.getLogger(PaymentServiceHelper.class);

	@Autowired
	private ProviderRequestResponseDao providerRequestResponseDao;

	@Autowired
	private Gson gson;

	@Autowired
	SignatureCreator sigCreator;

	@Autowired
	SHA256RSASignatureVerifier sigVerify;

	public void saveProviderRequestResponse(String transactionReference, HttpRequest httpRequest,
			ResponseEntity<String> response) {
		ProviderRequestResponseDto providerRequestResponseDto = ProviderRequestResponseDto.builder()
				.transactionReference(transactionReference)
				.request(gson.toJson(httpRequest)).response(response.getBody())
				.status(response.getStatusCodeValue()).build();
		providerRequestResponseDao.saveProviderRequestResponse(providerRequestResponseDto);
	}

	// signature, data, uuid, method
	public boolean isResponseSignatureValid(String inputSignature, Object data, String uuid, String method) {
		if(null == inputSignature) {
			return false;
		}

		try {
			JsonNode jsonNode = JsonUtils.toJsonNode(data);
			String serializedData = sigCreator.serializeData(jsonNode);

			String plainText = method + uuid + serializedData;

			LogMessage.debug(LOGGER, "inputSignature:" + inputSignature + "|serializedData:" + serializedData + "|plainText:" + plainText);

			if (sigVerify.verifySignature(inputSignature, plainText)) {
				LogMessage.log(LOGGER, " Signature Valid|inputSignature:" + inputSignature 
						+ "|uuid:" + uuid);

				return true;
			}		
		} catch (Exception e) {
			LogMessage.log(LOGGER, " Exception while validating signature::uuid:" + uuid);
			LogMessage.logException(LOGGER, e);
		}

		return false;
	}

}
