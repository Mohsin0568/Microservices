package com.systa.applications.springsecurity.dao;

import com.systa.applications.springsecurity.entity.UserPassword;

public interface UserPasswordRepository {

	public void save(UserPassword password);
	
	public UserPassword getUserActivePassword(Integer userId);
	
}
