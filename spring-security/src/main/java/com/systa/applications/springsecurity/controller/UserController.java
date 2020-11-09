package com.systa.applications.springsecurity.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.systa.applications.springsecurity.beans.UserLoginRequest;
import com.systa.applications.springsecurity.beans.UserRegistrationBean;
import com.systa.applications.springsecurity.entity.User;
import com.systa.applications.springsecurity.exceptions.UserNotFoundException;
import com.systa.applications.springsecurity.security.JwtTokenUtil;
import com.systa.applications.springsecurity.security.JwtUser;
import com.systa.applications.springsecurity.services.UserService;

@RestController
public class UserController {
	
	private static final Logger logger = LogManager.getLogger(UserController.class);

	@Autowired
	UserService userService;
	
	@Autowired
	JwtTokenUtil tokenUtil; 
	
	@RequestMapping(method = RequestMethod.GET, path = "/demo")
	public ResponseEntity<User> demo() {
		User user = new User();
		user.setId(1);
		if(user.getId() == 1)
			throw new UserNotFoundException("User not found");
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/user/{id}")
	public ResponseEntity<User> getUserDetails(@PathVariable("id") Integer id){
		logger.debug("Getting user details with id " + id);
		User user = userService.findUserById(id);
		if(user == null)
			throw new UserNotFoundException("User with id- " + id + " not found");
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/user")
	public ResponseEntity<Object> saveUserDetails(@Valid @RequestBody UserRegistrationBean user){
		logger.debug("Creating user with loginid " + user.getLoginid());
		User savedUser = userService.registerUser(user);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();		
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/userLogin")
	public ResponseEntity<Map<String, Object>> login(@RequestBody UserLoginRequest loginRequest) {
		Map<String, Object> response = new HashMap<String, Object>();
		User user = userService.findUserByLoginId(loginRequest.getUserName());
		if(user == null)
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.UNAUTHORIZED);
		if(userService.validatelogin(loginRequest)) {			
			JwtUser jwtUser = new JwtUser(user.getId(), user.getLoginid(), user.getFname(), user.getLname(), user.getEmail(), null, user.getActive());
			String token = tokenUtil.generateToken(jwtUser);
			response.put("token", token);
			response.put("user", user);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
		else
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.UNAUTHORIZED);
		
	}
}
