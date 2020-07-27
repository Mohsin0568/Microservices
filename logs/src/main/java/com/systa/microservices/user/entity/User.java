package com.systa.microservices.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Setter;

@Entity
@Table(name = "userDetails")
@Setter
public class User {

	private long id;
	private String userName, password, email, mobileNumber, city;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}
	
	@Column(name = "username", nullable = false)
	public String getUserName() {
		return userName;
	}
	
	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}
	
	@Column(name = "email", nullable = false)
	public String getEmail() {
		return email;
	}
	
	@Column(name = "mobilenumber", nullable = false)
	public String getMobileNumber() {
		return mobileNumber;
	}
	
	@Column(name = "city", nullable = false)
	public String getCity() {
		return city;
	}	
}
