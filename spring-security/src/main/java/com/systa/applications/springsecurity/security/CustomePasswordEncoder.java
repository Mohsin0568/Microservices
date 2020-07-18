package com.systa.applications.springsecurity.security;

import java.nio.charset.StandardCharsets;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class CustomePasswordEncoder implements PasswordEncoder {	
	
	@Override
	public String encode(CharSequence rawPassword) {
		byte[] passwordBytes = rawPassword.toString().getBytes(StandardCharsets.UTF_8);
		return Base64.encodeBase64String(passwordBytes);
	}
	
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {		
		return true;		
	}

}
