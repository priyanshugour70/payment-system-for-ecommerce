package com.cpt.payments.dao.Impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cpt.payments.dao.TransactionDetailsDao;
import com.cpt.payments.dto.TransactionDetails;
import com.cpt.payments.util.LogMessage;

@Repository
public class TransactionDetailsDaoImpl implements TransactionDetailsDao {
	private static final Logger LOGGER = LogManager.getLogger(TransactionDetailsDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public TransactionDetails getTransactionDetailsById(String code) {
		LogMessage.log(LOGGER, " :: fetching transactionDetails for :: " + code);

		TransactionDetails transactionDetails = null;
		try {
			transactionDetails = namedJdbcTemplate.queryForObject(getTransactionDetailsById(),
					new BeanPropertySqlParameterSource(TransactionDetails.builder().code(code).build()),
					new BeanPropertyRowMapper<>(TransactionDetails.class));
			LogMessage.log(LOGGER, " :: transaction Details from DB  = " + transactionDetails);
		} catch (Exception e) {
			LogMessage.log(LOGGER, "unable to get transaction Details " + e);
			LogMessage.logException(LOGGER, e);
		}
		return transactionDetails;
	}

	private String getTransactionDetailsById() {
		StringBuilder queryBuilder = new StringBuilder("SELECT * FROM  Transaction_Details WHERE code = :code ");
		LogMessage.log(LOGGER, "getTransactionDetailsById -> " + queryBuilder);
		return queryBuilder.toString();
	}
}
