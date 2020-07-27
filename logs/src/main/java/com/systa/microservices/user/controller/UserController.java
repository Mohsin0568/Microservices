package com.systa.microservices.user.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.systa.microservices.exceptions.ResourceNotFoundException;
import com.systa.microservices.user.entity.User;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	
	@Autowired
	UserService userService;

	@GetMapping("/users")
    public List <User> getAllUsers() {
        return userService.getAllUsers();
    }
	
	@GetMapping("/user/{id}")
    public ResponseEntity <User> getUserById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        User user = userService.getUserById(id);
        return ResponseEntity.ok().body(user);
    }
	
	@PostMapping("/user")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId()).toUri();
        // created user location will be sent in a response header
		return ResponseEntity.created(location).build();
    }
	
	@PutMapping("/user/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable(value = "id") Long id, @RequestBody User userDetails) throws ResourceNotFoundException {
		userDetails.setId(id);
        userService.updateUser(userDetails);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
	
	@DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Long id)
    throws ResourceNotFoundException {
        userService.deleteUser(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
