package com.cpt.payments.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result {
	private String signature;
	private String uuid;
	private String method;
	private ResponseData data;
}
