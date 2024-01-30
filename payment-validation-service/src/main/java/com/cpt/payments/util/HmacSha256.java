package com.cpt.payments.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class HmacSha256 {
	
	private static final Logger LOGGER = LogManager.getLogger(HmacSha256.class);
	public static final String HEX_ARRAY = "0123456789abcdef";

	public String generateHmac256(String message, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode;
		try {
			jsonNode = objectMapper.readValue(message, JsonNode.class);
			String response = jsonNode.toString();
			LogMessage.log(LOGGER, ":: minified json String is :: "+response);
			byte[] bytes = hmac(key, response.getBytes());
			return bytesToHex(bytes);
		} catch (JsonProcessingException e) {
			LogMessage.log(LOGGER, "Exception in generateHmac256" + e);
			e.printStackTrace();
		}
		return "";
	}

	byte[] hmac(byte[] key, byte[] message) throws NoSuchAlgorithmException, InvalidKeyException {
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(new SecretKeySpec(key, "HmacSHA256"));
		return mac.doFinal(message);
	}

	String bytesToHex(byte[] bytes) {
		final char[] hexArray = HEX_ARRAY.toCharArray();
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0, v; j < bytes.length; j++) {
			v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
}
