package com.systa.applications.springsecurity.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.systa.applications.springsecurity.entity.User;
import com.systa.applications.springsecurity.entity.UserPassword;
import com.systa.applications.springsecurity.services.UserPasswordService;
import com.systa.applications.springsecurity.services.UserService;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserService userService;
	
	@Autowired
	UserPasswordService passwordService;
	
    @Override
    public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	User user = userService.findUserByLoginId(username);
    	if(user == null)
    		return null;
    	UserPassword password = passwordService.getUserActivePassword(user.getId());
    	
    	return new JwtUser(user.getId(), user.getLoginid(), user.getFname(), user.getLname(), 
    			user.getEmail(), password.getPassword(), user.getActive());
    }
}
