package com.systa.applications.springsecurity.services;

import com.systa.applications.springsecurity.beans.UserLoginRequest;
import com.systa.applications.springsecurity.beans.UserRegistrationBean;
import com.systa.applications.springsecurity.entity.User;

public interface UserService {
	
	User findUserById(Integer id);
	
	User saveUser(User user);
	
	User registerUser(UserRegistrationBean userRegistration);
	
	User findUserByLoginId(String loginId);
	
	boolean validatelogin(UserLoginRequest loginRequest);
}
