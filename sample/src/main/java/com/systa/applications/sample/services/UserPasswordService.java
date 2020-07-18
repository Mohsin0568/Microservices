package com.systa.applications.sample.services;

import com.systa.applications.sample.entity.UserPassword;

public interface UserPasswordService {

	void saveUserPassword(UserPassword password);
	
	UserPassword getUserActivePassword(Integer userId);
}
