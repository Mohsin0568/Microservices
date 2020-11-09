package com.systa.applications.springsecurity.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.systa.applications.springsecurity.dao.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UniqueEmailIdValidator implements ConstraintValidator<UniqueEmailId, String>{

	UserRepository userRepository;
	
	@Override
	public boolean isValid(String emailId, ConstraintValidatorContext arg1) {
			return emailId != null && userRepository.findByEmail(emailId) == null;		
	}
}
