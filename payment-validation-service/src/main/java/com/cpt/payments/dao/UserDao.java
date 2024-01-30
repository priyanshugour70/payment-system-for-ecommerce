package com.cpt.payments.dao;

import com.cpt.payments.dto.User;

public interface UserDao {

	User getUserDetails(String email);

	Long insertUserDetails(User user);

}
