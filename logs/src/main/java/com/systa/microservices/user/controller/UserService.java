package com.systa.microservices.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systa.microservices.exceptions.ResourceNotFoundException;
import com.systa.microservices.user.entity.User;
import com.systa.microservices.user.repository.UserDetailsRepository;

@Service
public class UserService {

	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	public List<User> getAllUsers(){
		return userDetailsRepository.findAll();
	}
	
	public User getUserById(long id) throws ResourceNotFoundException{
		return userDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found - id: " + id));
	}
	
	public User saveUser(User user){
		return userDetailsRepository.save(user);
	}
	
	public User updateUser(User user) throws ResourceNotFoundException{
		User user1 = userDetailsRepository.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException("user not found to update - id: " + user.getId()));		
		user1.setEmail(user.getEmail());
		user1.setMobileNumber(user.getMobileNumber());
		user1.setCity(user.getCity());
		return userDetailsRepository.save(user1);
		
	}
	
	public void deleteUser(Long id) throws ResourceNotFoundException{
		User user = userDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found to delete - id: " + id));
		userDetailsRepository.delete(user);		
	}
	
}
