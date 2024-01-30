package com.cpt.payments.service.status.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.TransactionDetailsEnum;
import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.dao.TransactionDao;
import com.cpt.payments.dao.TransactionDetailsDao;
import com.cpt.payments.dao.TransactionLogDao;
import com.cpt.payments.dto.Transaction;
import com.cpt.payments.dto.TransactionDetails;
import com.cpt.payments.dto.TransactionLog;
import com.cpt.payments.service.TransactionStatusHandler;
import com.cpt.payments.util.LogMessage;

@Component
public class CreatedTransactionStatusHandler extends TransactionStatusHandler {
	private static final Logger LOGGER = LogManager.getLogger(CreatedTransactionStatusHandler.class);

	@Autowired
	private TransactionDetailsDao transactionDetailsDao;

	@Autowired
	private TransactionDao transactionDao;

	@Autowired
	private TransactionLogDao transactionLogDao;

	@Override
	public boolean updateStatus(Transaction transaction) {
		LogMessage.log(LOGGER, " creating new transaction -> " + transaction);
		TransactionDetails transactionDetails = transactionDetailsDao
				.getTransactionDetailsById(TransactionDetailsEnum.CREATED.getCode());
		transaction.setTxnDetailsId(transactionDetails.getId());
		transaction = transactionDao.createTransaction(transaction);
		if (null == transaction) {
			LogMessage.log(LOGGER, " creating new transaction failed -> " + transaction);
			return false;
		}
		TransactionLog transactionLog = TransactionLog.builder().transactionId(transaction.getId()).txnFromStatus("-")
				.txnToStatus(TransactionStatusEnum.CREATED.getName()).build();
		transactionLogDao.createTransactionLog(transactionLog);
		
		return true;
	}

}
