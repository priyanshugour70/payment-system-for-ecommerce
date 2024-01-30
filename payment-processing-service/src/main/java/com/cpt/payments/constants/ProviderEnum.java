package com.cpt.payments.constants;

import lombok.Getter;

public enum ProviderEnum {

	TRUSTLY(1, "TRUSTLY");

	@Getter
	private Integer providerId;
	@Getter
	private String providerName;

	private ProviderEnum(Integer providerId, String providerName) {
		this.providerId = providerId;
		this.providerName = providerName;
	}

	public static ProviderEnum getProviderEnum(String providerName) {
		for (ProviderEnum e : ProviderEnum.values()) {
			if (providerName.equals(e.providerName))
				return e;
		}
		return null;
	}
	
	public static ProviderEnum getProviderEnumById(int id) {
		for (ProviderEnum e : ProviderEnum.values()) {
			if (id == e.providerId)
				return e;
		}
		return null;
	}

}
