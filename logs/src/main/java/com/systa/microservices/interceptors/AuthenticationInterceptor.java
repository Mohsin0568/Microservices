package com.systa.microservices.interceptors;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.systa.microservices.security.JwtUtil;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	JwtUtil jwtUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String jwtToken = request.getHeader("Authorization");
		String userName = jwtUtil.getUsernameFromToken(jwtToken);
		String mobileNumber = jwtUtil.getMobileNumberFromToken(jwtToken);
		String uuid = UUID.randomUUID().toString();
		
		String serviceId = request.getRequestURI();
		
		// adding MDC values
		
		ThreadContext.put("userName", userName);
		ThreadContext.put("mobileNumber", mobileNumber);
		ThreadContext.put("serviceId", serviceId);
		ThreadContext.put("uuid", uuid);
		
		return super.preHandle(request, response, handler);
	}	
}
