package com.cpt.payments.service;

import com.cpt.payments.pojo.AddRequest;
import com.cpt.payments.util.HmacSha256Utils;
import com.google.gson.Gson;

public class TestService {
	
	private String secretKey = "ecom-ct-secret123";
	
	
	public int validateAndProcess(AddRequest req) {
		
		Gson gson = new Gson();
		String jsonRequest = gson.toJson(req);
		
		String generatedSig = HmacSha256Utils.generateSignature(secretKey, jsonRequest);
		
		System.out.println("inputString : " + jsonRequest);
		System.out.println("generatedSig : " + generatedSig);
		
		return -1;
	}

}
