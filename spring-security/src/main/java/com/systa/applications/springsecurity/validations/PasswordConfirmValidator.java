package com.systa.applications.springsecurity.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.systa.applications.springsecurity.beans.UserRegistrationBean;

public class PasswordConfirmValidator implements ConstraintValidator<PasswordConfirm, Object>{

	@Override
	public boolean isValid(Object userRegistrationBean, ConstraintValidatorContext arg1) {
		String password = ((UserRegistrationBean)userRegistrationBean).getPassword();
		String confirmPassword = ((UserRegistrationBean)userRegistrationBean).getConfirmPassword();
		return password.equals(confirmPassword);
	}

}
