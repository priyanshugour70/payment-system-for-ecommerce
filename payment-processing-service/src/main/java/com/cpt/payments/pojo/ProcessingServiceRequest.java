package com.cpt.payments.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessingServiceRequest {
	private long transactionId;
	private String firstName;
	private String lastName;
	private String email;
	
}
