package com.systa.applications.sample.beans;

import java.io.Serializable;

public class UserLoginRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String userName, password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	
}