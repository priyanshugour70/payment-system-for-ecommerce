package com.cpt.payments.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Attributes {
	private String country;
    private String locale;
    private String currency;
    private double amount;
    private String iP;
    private String mobilePhone;
    private String firstname;
    private String lastname;
    private String email;
    private String nationalIdentificationNumber;
    private String successURL;
    private String failURL;
}
