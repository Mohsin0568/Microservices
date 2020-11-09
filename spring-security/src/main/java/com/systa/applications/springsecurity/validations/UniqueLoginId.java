package com.systa.applications.springsecurity.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueLoginIdValidator.class)
public @interface UniqueLoginId {

	String message() default "Loginid already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
