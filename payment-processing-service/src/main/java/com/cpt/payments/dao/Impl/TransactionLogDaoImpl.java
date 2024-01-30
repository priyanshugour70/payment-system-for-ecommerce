package com.cpt.payments.dao.Impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cpt.payments.dao.TransactionLogDao;
import com.cpt.payments.dto.TransactionLog;
import com.cpt.payments.util.LogMessage;

@Repository
public class TransactionLogDaoImpl implements TransactionLogDao {
	private static final Logger LOGGER = LogManager.getLogger(TransactionLogDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public void createTransactionLog(TransactionLog transactionLog) {

		try {
			namedJdbcTemplate.update(createTransactionLog(), new BeanPropertySqlParameterSource(transactionLog));
		} catch (Exception e) {
			LogMessage.log(LOGGER, "exception while saving transaction log in DB :: " + transactionLog);
			LogMessage.logException(LOGGER, e);
		}

	}

	private String createTransactionLog() {
		StringBuilder queryBuilder = new StringBuilder("INSERT INTO Transaction_Log ");
		queryBuilder.append("(transactionId, txnFromStatus, txnToStatus) ");
		queryBuilder.append("VALUES(:transactionId, :txnFromStatus, :txnToStatus)");
		LogMessage.log(LOGGER, " Insert Transaction log query -> " + queryBuilder);
		return queryBuilder.toString();
	}
}
