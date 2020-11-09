package com.systa.applications.springsecurity.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.systa.applications.springsecurity.dao.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UniqueLoginIdValidator implements ConstraintValidator<UniqueLoginId, String>{
	
	UserRepository userRepository;
	
	@Override
	public boolean isValid(String loginId, ConstraintValidatorContext arg1) {
		return loginId != null && userRepository.findByLoginid(loginId) == null ;
	}

}
