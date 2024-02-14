package com.cpt.payments.pojo.response;

import com.cpt.payments.pojo.response.error.ErrorWrapper;

import lombok.Data;

@Data
public class TrustlyCoreResponse {
	private String version;

	private Result result;
	private ErrorWrapper error;
}
