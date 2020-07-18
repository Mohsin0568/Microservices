package com.systa.applications.sample.services.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systa.applications.sample.dao.UserPasswordRepository;
import com.systa.applications.sample.entity.UserPassword;
import com.systa.applications.sample.services.UserPasswordService;

@Service
public class UserPasswordServiceImpl implements UserPasswordService {

	@Autowired
	UserPasswordRepository userPasswordRepository;
	
	@Override
	@Transactional
	public void saveUserPassword(UserPassword password) {
		userPasswordRepository.save(password);
	}

	@Override
	@Transactional
	public UserPassword getUserActivePassword(Integer userId) {
		return userPasswordRepository.getUserActivePassword(userId);
	}

}
