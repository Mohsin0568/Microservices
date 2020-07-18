package com.systa.applications.sample.services.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systa.applications.sample.beans.UserLoginRequest;
import com.systa.applications.sample.beans.UserRegistrationBean;
import com.systa.applications.sample.dao.UserRepository;
import com.systa.applications.sample.entity.User;
import com.systa.applications.sample.entity.UserPassword;
import com.systa.applications.sample.services.UserPasswordService;
import com.systa.applications.sample.services.UserService;
import com.systa.applications.sample.util.PasswordEncryption;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserPasswordService userPasswordService;
	
	
	@Override
	public User findUserById(Integer id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent())
			return user.get();
		else
			return null;
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public User registerUser(UserRegistrationBean userRegistration) {
		User user = createUserObject(userRegistration);
		user.setActive(1);
		user = saveUser(user);
		UserPassword password = new UserPassword();		
		password.setPassword(PasswordEncryption.encryptPassword(userRegistration.getPassword()));
		password.setActive(1);
		password.setUser(user);
		userPasswordService.saveUserPassword(password);
		return user;
	}
	
	public User createUserObject(UserRegistrationBean userRegistration) {
		User user = new User();
		user.setEmail(userRegistration.getEmail());
		user.setFname(userRegistration.getFname());
		user.setLname(userRegistration.getLname());
		user.setLoginid(userRegistration.getLoginid());
		user.setMobileNumber(userRegistration.getMobileNumber());		
		return user;
	}

	@Override
	public User findUserByLoginId(String loginId) {
		return userRepository.findByLoginid(loginId);
	}

	@Override
	public boolean validatelogin(UserLoginRequest loginRequest) {
		User user = findUserByLoginId(loginRequest.getUserName());
		UserPassword userPassword = userPasswordService.getUserActivePassword(user.getId());
		String userEncPassword = PasswordEncryption.encryptPassword(loginRequest.getPassword());
		if(userPassword.getPassword().equals(userEncPassword))
			return true;
		return false;
	}

}
