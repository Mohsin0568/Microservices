package com.systa.licencing.utils;

import org.springframework.context.annotation.Bean;

import feign.RequestInterceptor;

public class FeignInterceptorUtils {
	
	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
			requestTemplate.header(UserContext.CORRELATION_ID, UserContextHolder.getUserContext().getCorrelationId());
		};
	}

}
