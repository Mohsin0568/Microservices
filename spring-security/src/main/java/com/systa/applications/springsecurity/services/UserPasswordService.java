package com.systa.applications.springsecurity.services;

import com.systa.applications.springsecurity.entity.UserPassword;

public interface UserPasswordService {

	void saveUserPassword(UserPassword password);
	
	UserPassword getUserActivePassword(Integer userId);
}
