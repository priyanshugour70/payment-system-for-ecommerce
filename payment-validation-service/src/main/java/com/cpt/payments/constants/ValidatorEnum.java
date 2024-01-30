package com.cpt.payments.constants;

import com.cpt.payments.service.Validator;
import com.cpt.payments.service.impl.validators.AmountValidator;
import com.cpt.payments.service.impl.validators.CreditorAccountNumberValidator;
import com.cpt.payments.service.impl.validators.CurrencyValidator;
import com.cpt.payments.service.impl.validators.DebitorAccountNumberValidator;
import com.cpt.payments.service.impl.validators.DuplicateTransactionValidator;
import com.cpt.payments.service.impl.validators.EmailValidator;
import com.cpt.payments.service.impl.validators.FirstNameValidator;
import com.cpt.payments.service.impl.validators.LastNameValidator;
import com.cpt.payments.service.impl.validators.MerchantTransactionIDValidator;
import com.cpt.payments.service.impl.validators.PaymentMethodValidator;
import com.cpt.payments.service.impl.validators.PaymentRequestValidator;
import com.cpt.payments.service.impl.validators.PaymentTypeValidator;
import com.cpt.payments.service.impl.validators.PhoneNumberValidator;
import com.cpt.payments.service.impl.validators.ProviderIdValidator;
import com.cpt.payments.service.impl.validators.SignatureValidator;

import lombok.Getter;

public enum ValidatorEnum {

	SIGNATURE_CHECK_FILTER("SIGNATURE_CHECK_FILTER", SignatureValidator.class),
	PAYMENT_REQUEST_FILTER("PAYMENT_REQUEST_FILTER", PaymentRequestValidator.class),
	DUPLICATE_TXN_FILTER("DUPLICATE_TXN_FILTER", DuplicateTransactionValidator.class),
	MERCHANT_TXN_ID_FILTER("MERCHANT_TXN_ID_FILTER", MerchantTransactionIDValidator.class),
	FIRST_NAME_FILTER("FIRST_NAME_FILTER", FirstNameValidator.class),
	LAST_NAME_FILTER("LAST_NAME_FILTER", LastNameValidator.class),
	CUSTOMER_EMAIL_FILTER("CUSTOMER_EMAIL_FILTER", EmailValidator.class),
	PHONE_NUMBER_FILTER("PHONE_NUMBER_FILTER", PhoneNumberValidator.class),
	PAYMENT_METHOD_FILTER("PAYMENT_METHOD_FILTER", PaymentMethodValidator.class),
	PAYMENT_TYPE_FILTER("PAYMENT_TYPE_FILTER", PaymentTypeValidator.class),
	AMOUNT_FILTER("AMOUNT_FILTER", AmountValidator.class), CURRENCY_FILTER("CURRENCY_FILTER", CurrencyValidator.class),
	PROVIDER_ID_FILTER("PROVIDER_ID_FILTER", ProviderIdValidator.class),
	CREDITOR_ACCOUNT_NUMBER("CREDITOR_ACCOUNT_NUMBER", CreditorAccountNumberValidator.class),
	DEBITOR_ACCOUNT_NUMBER("DEBITOR_ACCOUNT_NUMBER", DebitorAccountNumberValidator.class);

	@Getter
	private String validatorName;

	private Class<? extends Validator> validatorClass;

	private ValidatorEnum(String validatorName, Class<? extends Validator> validatorClass) {
		this.validatorName = validatorName;
		this.validatorClass = validatorClass;
	}

	/**
	 * Gets the enum by string.
	 *
	 * @param code the code
	 * @return the enum by string
	 */
	public static ValidatorEnum getEnumByString(String name) {
		for (ValidatorEnum e : ValidatorEnum.values()) {
			if (name.equals(e.validatorName))
				return e;
		}
		return null;
	}

	public Class<? extends Validator> getValidatorClass() {
		return validatorClass;
	}
}
