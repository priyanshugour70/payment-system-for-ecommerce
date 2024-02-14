package com.cpt.payments.pojo.response;

import lombok.Data;

@Data
public class Result {
	private String signature;
	private String uuid;
	private String method;
	private ResponseData data;
}
