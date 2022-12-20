package com.systa.licencing.utils;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class UserContext {

	public static final String CORRELATION_ID = "tmx-correlation-id";
	public static final String AUTH_TOKEN = "tmx-auth-token";
	public static final String USER_ID = "tmx-user-id";
	public static final String ORGANIZATION_ID = "tmx-organization-id";
	
	private String correlationId = new String();
	private String authToken = new String();
	private String userId = new String();
	private String orgnaizationId = new String();
	
	
}