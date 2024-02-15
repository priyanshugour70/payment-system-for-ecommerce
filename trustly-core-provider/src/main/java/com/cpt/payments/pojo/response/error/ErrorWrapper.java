package com.cpt.payments.pojo.response.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorWrapper {
	
	private String name;
	private String code;
	private String message;
	private ErrorDetails error;
	
}
