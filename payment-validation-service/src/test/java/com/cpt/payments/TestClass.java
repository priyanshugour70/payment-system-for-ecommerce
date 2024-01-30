package com.cpt.payments;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import com.cpt.payments.pojo.Payment;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.pojo.User;
import com.cpt.payments.util.HmacSha256;
import com.google.gson.Gson;

public class TestClass {

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException {
		User user = new User();
		user.setEmail("johnpeter@gmail.com");
		user.setFirstName("john");
		user.setLastName("peter");
		user.setPhoneNumber("+919393939393");

		Payment payments = new Payment();
		payments.setAmount("18.00");
		payments.setCreditorAccount("4242424242424242");
		payments.setCurrency("EUR");
		payments.setDebitorAccount("4111111111111111");
		payments.setMerchantTransactionReference("cptraining_test8");
		payments.setPaymentMethod("APM");
		payments.setPaymentType("SALE");
		payments.setProviderId("Trustly");


		PaymentRequest paymentRequest = new PaymentRequest();
		paymentRequest.setPayment(payments);
		paymentRequest.setUser(user);


		HmacSha256 signatureGenerator = new HmacSha256();
		
		Gson gson = new Gson();
		String reqestJSON = gson.toJson(paymentRequest);
		System.out.println(reqestJSON);
		
		String signature = signatureGenerator.generateHmac256(reqestJSON, "cptTraining".getBytes());
		
		System.out.println(signature);

	}


}
