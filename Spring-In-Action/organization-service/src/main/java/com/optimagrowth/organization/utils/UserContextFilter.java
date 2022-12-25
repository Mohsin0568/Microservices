package com.optimagrowth.organization.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class UserContextFilter  implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		UserContextHolder.getUserContext().setCorrelationId(httpRequest.getHeader(UserContext.CORRELATION_ID));
		UserContextHolder.getUserContext().setAuthToken(httpRequest.getHeader(UserContext.AUTH_TOKEN));
		UserContextHolder.getUserContext().setOrgnaizationId(httpRequest.getHeader(UserContext.ORGANIZATION_ID));
		UserContextHolder.getUserContext().setUserId(httpRequest.getHeader(UserContext.USER_ID));
		
		chain.doFilter(httpRequest, response);
	}

}
