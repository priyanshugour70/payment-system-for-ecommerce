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
public class PendingTransactionStatusHandler extends TransactionStatusHandler {

	private static final Logger LOGGER = LogManager.getLogger(PendingTransactionStatusHandler.class);

	@Autowired
	private TransactionDetailsDao transactionDetailsDao;

	@Autowired
	private TransactionDao transactionDao;

	@Autowired
	private TransactionLogDao transactionLogDao;

	@Override
	public boolean updateStatus(Transaction transaction) {
		LogMessage.log(LOGGER, " transaction PENDING -> " + transaction);
		TransactionDetails transactionDetails = transactionDetailsDao
				.getTransactionDetailsById(TransactionDetailsEnum.PENDING.getCode());
		transaction.setTxnDetailsId(transactionDetails.getId());
		transaction.setTxnStatusId(TransactionStatusEnum.PENDING.getId());
		boolean transactionStatus = transactionDao.updateTransaction(transaction);
		if (!transactionStatus) {
			LogMessage.log(LOGGER, " updating transaction failed -> " + transaction);
			return false;
		}
		TransactionLog transactionLog = TransactionLog.builder().transactionId(transaction.getId())
				.txnFromStatus(TransactionStatusEnum.CREATED.getName())
				.txnToStatus(TransactionStatusEnum.PENDING.getName()).build();
		transactionLogDao.createTransactionLog(transactionLog);
		return true;
	}

}
