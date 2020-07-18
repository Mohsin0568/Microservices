package com.systa.applications.sample.dao;

import com.systa.applications.sample.entity.UserPassword;

public interface UserPasswordRepository {

	public void save(UserPassword password);
	
	public UserPassword getUserActivePassword(Integer userId);
	
}
