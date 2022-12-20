package com.systa.licencing.utils;

public class UserContextHolder {
	
	private static final ThreadLocal<UserContext> userContext = new ThreadLocal<UserContext>();
	
	public static final UserContext getUserContext() {
		UserContext context = userContext.get();
		
		if(context == null) {
			setContext();
		}
		
		return userContext.get();
	}
	
	public static final void setContext(UserContext context) {
		userContext.set(context);
	}
	
	public static final void setContext() {
		UserContext context = new UserContext();
		userContext.set(context);
	}

}
