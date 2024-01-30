package com.cpt.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TransactionLog {
	private Integer id;
	private Integer transactionId;
	private String txnFromStatus;
	private String txnToStatus;
}
