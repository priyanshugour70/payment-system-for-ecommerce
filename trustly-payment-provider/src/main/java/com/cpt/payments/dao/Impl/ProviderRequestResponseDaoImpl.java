package com.cpt.payments.dao.Impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cpt.payments.dao.ProviderRequestResponseDao;
import com.cpt.payments.dto.ProviderRequestResponseDto;
import com.cpt.payments.util.LogMessage;

@Repository
public class ProviderRequestResponseDaoImpl implements ProviderRequestResponseDao {
	private static final Logger LOGGER = LogManager.getLogger(ProviderRequestResponseDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public void saveProviderRequestResponse(ProviderRequestResponseDto providerRequestResponse) {
		try {
			namedJdbcTemplate.update(saveProviderRequestResponseQuery(), 
					new BeanPropertySqlParameterSource(providerRequestResponse));
		} catch (Exception e) {
			LogMessage.warn(LOGGER, "exception while saving ProviderRequestResponse in DB :: " + providerRequestResponse);
			LogMessage.logException(LOGGER, e);
		}
	}
	

	private String saveProviderRequestResponseQuery() {
		StringBuilder queryBuilder = new StringBuilder("INSERT INTO provider_request_response ");
		queryBuilder.append("(transactionReference, request, response) ");
		queryBuilder.append("VALUES(:transactionReference, :request, :response)");
		LogMessage.log(LOGGER, " Insert ProviderRequestResponse query -> " + queryBuilder);
		return queryBuilder.toString();
	}

}
