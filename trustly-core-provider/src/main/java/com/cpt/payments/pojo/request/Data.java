package com.cpt.payments.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Data {
	 private String username;
	    private String password;
	    private String notificationURL;
	    private String endUserID;
	    private String messageID;
	    private Attributes attributes;
}
