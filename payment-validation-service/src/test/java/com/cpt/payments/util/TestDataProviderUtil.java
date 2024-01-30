package com.cpt.payments.util;

import com.cpt.payments.pojo.Payment;
import com.cpt.payments.pojo.User;

public class TestDataProviderUtil {

    public static User getTestUserBean() {
        User user = new User();
        user.setEmail("johnpeter@gmail.com");
        user.setFirstName("john");
        user.setLastName("peter");
        user.setPhoneNumber("9393939393");
        return user;
    }

    public static com.cpt.payments.dto.User getTestUserDto() {
        com.cpt.payments.dto.User user = new com.cpt.payments.dto.User();
        user.setId(123L);
        user.setEmail("johnpeter@gmail.com");
        user.setFirstName("john");
        user.setLastName("peter");
        user.setPhoneNumber("9393939393");
        return user;
    }

    public static Payment getTestPayment() {
        Payment payment = new Payment();
        payment.setAmount("18.0");
        payment.setCreditorAccount("4242424242424242");
        payment.setCurrency("EUR");
        payment.setDebitorAccount("4111111111111111");
        payment.setMerchantTransactionReference("cptraining_test3");
        payment.setPaymentMethod("APM");
        payment.setPaymentType("SALE");
        payment.setProviderId("TRUSTLY");
        return payment;
    }
}
