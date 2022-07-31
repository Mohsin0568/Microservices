package com.systa.microservices;

public class SessionContext {

	private SessionContext() {}
	
	private static final ThreadLocal<String> CONTEXT = new InheritableThreadLocal<>();
	
	public static String getSessionDetail() {
		return CONTEXT.get();
	}
	
	public static void setSessionDetail(String value) {
		CONTEXT.set(value);
	}
	
	public static void clear() {
		CONTEXT.remove();
	}
}
