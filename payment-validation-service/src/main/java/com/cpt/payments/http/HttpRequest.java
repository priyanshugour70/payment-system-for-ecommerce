package com.cpt.payments.http;

import org.springframework.http.HttpMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HttpRequest {
	private String url;
	private String request;
	private HttpMethod httpMethod;
}
