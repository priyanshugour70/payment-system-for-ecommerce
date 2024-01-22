package com.cpt.payments.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacSha256Utils {
	
	private HmacSha256Utils(){
		
	}
	
	public static String generateSignature(String secretKey, String jsonInput){
		
		String signature = null ;
		
		try {
			
			SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
			
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(keySpec);
			
			byte[] signatureBytes = mac.doFinal(jsonInput.getBytes(StandardCharsets.UTF_8));
			
			signature = Base64.getEncoder().encodeToString(signatureBytes);
			
		} catch ( NoSuchAlgorithmException | InvalidKeyException e) {
			e.printStackTrace();
		}
		return signature;
	}

}
