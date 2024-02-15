package com.cpt.payments.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Params {
	private String signature;
	private String uuid;
	private Data data;
}
