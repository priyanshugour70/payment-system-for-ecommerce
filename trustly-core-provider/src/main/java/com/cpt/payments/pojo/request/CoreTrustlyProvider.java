package com.cpt.payments.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoreTrustlyProvider {
	private String method;
    private Params params;
    private String version;
}
