package com.cpt.payments.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.cpt.payments.dao.UserDao;
import com.cpt.payments.dto.User;
import com.cpt.payments.util.LogMessage;

@Repository
public class UserDaoImpl implements UserDao {
	private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public User getUserDetails(String email) {

		LogMessage.log(LOGGER, " :: fetching user Details  for :: " + email);

		User user = null;
		try {
			user = namedJdbcTemplate.queryForObject(getUserDetails(),
					new BeanPropertySqlParameterSource(User.builder().email(email).build()),
					new BeanPropertyRowMapper<>(User.class));
			LogMessage.log(LOGGER, " :: user Details from DB  = " + user);
		} catch (Exception e) {
			LogMessage.log(LOGGER, "unable to get user Details " + e);
			LogMessage.logException(LOGGER, e);
		}
		return user;

	}

	private String getUserDetails() {
		StringBuilder queryBuilder = new StringBuilder("SELECT * FROM  User WHERE email = :email ");
		LogMessage.log(LOGGER, "getMerchantPaymentRequest -> " + queryBuilder);
		return queryBuilder.toString();
	}

	@Override
	public Long insertUserDetails(User user) {
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			namedJdbcTemplate.update(insertUserDetails(), new BeanPropertySqlParameterSource(user), keyHolder);
			Long sessionId = keyHolder.getKey().longValue();
			user.setId(sessionId);
			return sessionId;
		} catch (Exception e) {
			LogMessage.log(LOGGER, "exception while saving user details in DB :: " + user);
			LogMessage.logException(LOGGER, e);
		}
		return null;
	}

	private String insertUserDetails() {
		StringBuilder queryBuilder = new StringBuilder("INSERT INTO User ");
		queryBuilder.append("(email, phoneNumber, firstName, lastName) ");
		queryBuilder.append("VALUES(:email, :phoneNumber, :firstName, :lastName)");
		LogMessage.log(LOGGER, " Insert user query -> " + queryBuilder);
		return queryBuilder.toString();
	}

}
