package com.systa.microservices.logger;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mohsin
 * This annotation will be used on a method and when used input output args of that method will be logged.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ METHOD })
public @interface Logger {

	String logLevel() default "DEBUG";
	
	// will be used if don't want to print input args in log
	boolean inputArgs() default true;
	
	// will be used if don't want to print output args in log
	boolean outputArgs() default true;
	
	// will be used if don't want to print either input or output args in log
	boolean isRequired() default true;
}
