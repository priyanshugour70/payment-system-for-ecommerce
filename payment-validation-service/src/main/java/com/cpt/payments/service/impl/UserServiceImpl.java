package com.cpt.payments.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.dao.UserDao;
import com.cpt.payments.dto.User;
import com.cpt.payments.exceptions.ValidationException;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.service.UserService;
import com.cpt.payments.util.LogMessage;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDao userDao;

	@Override
	public Long createUser(PaymentRequest paymentRequest) {
		LogMessage.log(LOGGER, " checking user details for -> "+paymentRequest);
		User user = userDao.getUserDetails(paymentRequest.getUser().getEmail());
		if(null == user) {
			LogMessage.log(LOGGER, " user details are empty, creating new user ");
			user = User.builder()
					.email(paymentRequest.getUser().getEmail())
					.firstName(paymentRequest.getUser().getFirstName())
					.lastName(paymentRequest.getUser().getLastName())
					.phoneNumber(paymentRequest.getUser().getPhoneNumber())
					.build();
			Long id = userDao.insertUserDetails(user);
			if(null == id) {
				throw new ValidationException(HttpStatus.INTERNAL_SERVER_ERROR,
						ErrorCodeEnum.FAILED_TO_CREATE_USER.getErrorCode(),
						ErrorCodeEnum.FAILED_TO_CREATE_USER.getErrorMessage());
			}
		}
		return user.getId();
	}

}
