package com.cpt.payments.pojo.response.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
	
	private String signature;
	private String uuid;
	private String method;
	private ErrorData data;

}
