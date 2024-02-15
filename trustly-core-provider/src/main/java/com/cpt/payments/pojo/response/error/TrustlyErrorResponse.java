package com.cpt.payments.pojo.response.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrustlyErrorResponse {
	private ErrorWrapper error;
	private String version;
}
