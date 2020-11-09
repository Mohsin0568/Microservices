package com.systa.applications.springsecurity.beans;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.systa.applications.springsecurity.validations.PasswordConfirm;
import com.systa.applications.springsecurity.validations.PasswordPolicy;
import com.systa.applications.springsecurity.validations.UniqueEmailId;
import com.systa.applications.springsecurity.validations.UniqueLoginId;

import lombok.Data;

@Data
@PasswordConfirm
public class UserRegistrationBean implements Serializable{

	private static final long serialVersionUID = 1L;	
	
	@NotEmpty(message="Please enter your firstname")
	@Size(min=8, max=20)
	private String fname;
	
	@NotEmpty(message="Please enter your lasttname")
	@Size(min=8, max=20)
	private String lname;
		
	@NotEmpty(message="Please enter your loginid")
	@UniqueLoginId
	private String loginid;	
	
	@NotEmpty(message="Please enter your email")
	@UniqueEmailId
	private String email;
	
	@NotEmpty(message="Please enter your mobileNumber")
	private String mobileNumber;
	
	@NotEmpty(message="Please provide password")
	@PasswordPolicy
	private String password;	
	
	@NotEmpty(message="Please provide confirm password")
	private String confirmPassword;
	
}
