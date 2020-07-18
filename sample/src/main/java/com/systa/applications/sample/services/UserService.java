package com.systa.applications.sample.services;

import com.systa.applications.sample.beans.UserLoginRequest;
import com.systa.applications.sample.beans.UserRegistrationBean;
import com.systa.applications.sample.entity.User;

public interface UserService {
	
	User findUserById(Integer id);
	
	User saveUser(User user);
	
	User registerUser(UserRegistrationBean userRegistration);
	
	User findUserByLoginId(String loginId);
	
	boolean validatelogin(UserLoginRequest loginRequest);
}
