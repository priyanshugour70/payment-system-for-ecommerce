package com.cpt.payments.dao.Impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.cpt.payments.dao.TransactionDao;
import com.cpt.payments.dto.Transaction;
import com.cpt.payments.util.LogMessage;

@Repository
public class TransactionImpl implements TransactionDao {
	private static final Logger LOGGER = LogManager.getLogger(TransactionImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public Transaction createTransaction(Transaction transaction) {

		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			namedJdbcTemplate.update(createTransaction(), new BeanPropertySqlParameterSource(transaction),
					keyHolder);
			Long sessionId = keyHolder.getKey().longValue();
			transaction.setId(sessionId.intValue());
		} catch (Exception e) {
			LogMessage.log(LOGGER, "exception while saving transaction details in DB :: " + transaction);
			LogMessage.logException(LOGGER, e);
		}
		return transaction;

	}

	private String createTransaction() {
		StringBuilder queryBuilder = new StringBuilder("INSERT INTO Transaction ");
		queryBuilder.append(
				"(userId, paymentMethodId, providerId, paymentTypeId, amount, currency, txnStatusId, txnReference, txnDetailsId, debitorAccount,creditorAccount, merchantTransactionReference) ");
		queryBuilder.append(
				"VALUES(:userId, :paymentMethodId, :providerId, :paymentTypeId, :amount, :currency, :txnStatusId, :txnReference, :txnDetailsId, :debitorAccount, :creditorAccount, :merchantTransactionReference)");
		LogMessage.log(LOGGER, " Insert Transaction query -> " + queryBuilder);
		return queryBuilder.toString();
	}

	@Override
	public boolean updateTransaction(Transaction transaction) {
		try {
			namedJdbcTemplate.update(updateTransaction(), new BeanPropertySqlParameterSource(transaction));
			return true;
		} catch (Exception e) {
			LogMessage.log(LOGGER, "exception while updating TRANSACTION in DB :: " + transaction);
			LogMessage.logException(LOGGER, e);
		}
		return false;
	}

	private String updateTransaction() {
		StringBuilder queryBuilder = new StringBuilder("Update Transaction ");
		queryBuilder.append("SET txnStatusId=:txnStatusId, txnDetailsId=:txnDetailsId ");
		queryBuilder.append("WHERE id=:id ");
		LogMessage.log(LOGGER, " " + "update Transaction query -> " + queryBuilder);
		return queryBuilder.toString();
	}

	@Override
	public Transaction getTransactionById(long transactionId) {
		LogMessage.log(LOGGER, " :: fetching Transaction Details  for :: " + transactionId);

		Transaction transaction = null;
		try {
			transaction = namedJdbcTemplate.queryForObject(getTransactionById(),
					new BeanPropertySqlParameterSource(Transaction.builder().id((int) transactionId).build()),
					new BeanPropertyRowMapper<>(Transaction.class));
			LogMessage.log(LOGGER, " :: transaction Details from DB  = " + transaction);
		} catch (Exception e) {
			LogMessage.log(LOGGER, "unable to get transaction Details " + e);
			LogMessage.logException(LOGGER, e);
		}
		return transaction;
	}

	private String getTransactionById() {
		StringBuilder queryBuilder = new StringBuilder("Select * from Transaction where id=:id ");
		LogMessage.log(LOGGER, " " + "getTransactionById query -> " + queryBuilder);
		return queryBuilder.toString();
	}

	@Override
	public void updateProviderReference(Transaction transaction) {
		try {
			namedJdbcTemplate.update(updateProviderReference(), new BeanPropertySqlParameterSource(transaction));
		} catch (Exception e) {
			LogMessage.log(LOGGER, "exception while updating TRANSACTION in DB :: " + transaction);
			LogMessage.logException(LOGGER, e);
		}

	}

	private String updateProviderReference() {
		StringBuilder queryBuilder = new StringBuilder("Update Transaction ");
		queryBuilder.append("SET providerReference=:providerReference ");
		queryBuilder.append("WHERE id=:id ");
		LogMessage.log(LOGGER, " " + "updateProviderReference query -> " + queryBuilder);
		return queryBuilder.toString();
	}

	@Override
	public Transaction getTransactionByProviderReference(String paymentId) {
		LogMessage.log(LOGGER, " :: fetching Transaction Details  for provider reference :: " + paymentId);

		Transaction transaction = null;
		try {
			transaction = namedJdbcTemplate.queryForObject(getTransactionByProviderReference(),
					new BeanPropertySqlParameterSource(Transaction.builder().providerReference(paymentId).build()),
					new BeanPropertyRowMapper<>(Transaction.class));
			LogMessage.log(LOGGER, " :: transaction Details from DB  = " + transaction);
		} catch (Exception e) {
			LogMessage.log(LOGGER, "unable to get transaction Details " + e);
			LogMessage.logException(LOGGER, e);
		}
		return transaction;
	}

	private String getTransactionByProviderReference() {
		StringBuilder queryBuilder = new StringBuilder("Select * from Transaction where providerReference=:providerReference ");
		LogMessage.log(LOGGER, "getTransactionByProviderReference query -> " + queryBuilder);
		return queryBuilder.toString();
	}

	@Override
	public void updateProviderCodeAndMessage(Transaction transaction) {
		try {
			namedJdbcTemplate.update(updateProviderCodeAndMessage(), new BeanPropertySqlParameterSource(transaction));
		} catch (Exception e) {
			LogMessage.log(LOGGER, "exception while updating TRANSACTION in DB :: " + transaction);
			LogMessage.logException(LOGGER, e);
		}
	}

	private String updateProviderCodeAndMessage() {
		StringBuilder queryBuilder = new StringBuilder("Update Transaction ");
		queryBuilder.append("SET providerCode=:providerCode, providerMessage=:providerMessage ");
		queryBuilder.append("WHERE id=:id ");
		LogMessage.log(LOGGER, " " + "updateProviderCodeAndMessage query -> " + queryBuilder);
		return queryBuilder.toString();
	}
}