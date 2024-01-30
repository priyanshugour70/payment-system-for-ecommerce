package com.cpt.payments.service.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.service.TransactionStatusHandler;
import com.cpt.payments.service.status.handler.ApprovedTransactionStatusHandler;
import com.cpt.payments.service.status.handler.CreatedTransactionStatusHandler;
import com.cpt.payments.service.status.handler.FailedTransactionStatusHandler;
import com.cpt.payments.service.status.handler.PendingTransactionStatusHandler;
import com.cpt.payments.util.LogMessage;


@Component
public class TransactionStatusFactory {

	private static final Logger LOGGER = LogManager.getLogger(TransactionStatusFactory.class);
	
	@Autowired
	private ApplicationContext context;

	public TransactionStatusHandler getStatusFactory(TransactionStatusEnum transactionStatusEnum) {
		LogMessage.log(LOGGER, " fetching transaction status handler for -> "+transactionStatusEnum);
		switch(transactionStatusEnum) {
		case CREATED:
			return context.getBean(CreatedTransactionStatusHandler.class);
		case APPROVED:
			return context.getBean(ApprovedTransactionStatusHandler.class);
		case FAILED:	
			return context.getBean(FailedTransactionStatusHandler.class);
		case PENDING:	
			return context.getBean(PendingTransactionStatusHandler.class);
		default:
			return null;
		}
	}
	
	
}
