package com.cpt.payments;

import com.cpt.payments.pojo.AddRequest;
import com.google.gson.Gson;

public class TestMain {
	
	public static void main(String[] args) {
		
		Gson gson = new Gson();
		
		AddRequest req = new AddRequest();
		
		req.setNum1(5);
		req.setNum2(10);
		
		System.out.println(gson.toJson(req));
		
	}
}
