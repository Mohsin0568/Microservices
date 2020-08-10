package com.systa.microservices.logger;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
	
	@Value("${framework.log.level}")
	private String logLevel;
	
	private static final String DEBUG_LEVEL = "DEBUG";
	private static final String ERROR_LEVEL = "ERROR";

	@Pointcut(value = "bean(*Controller)")
	public void pointCutForLoggingController(){
//		
		
	}
	
	@Around(value = "pointCutForLoggingController()")
	public Object aroundAdviceForControllerBean(ProceedingJoinPoint joinPoint) throws Throwable{
		String loggingLevel = (logLevel != null && !logLevel.isEmpty()) ? logLevel : DEBUG_LEVEL;
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		
		// before
		LogUtil.write(loggingLevel, method.getName() + "() started execution");
		logParams(joinPoint, method, loggingLevel);
		
		long startTime = System.currentTimeMillis();
		Object result = null;
		try{
			result = joinPoint.proceed();			
		}
		catch(Exception e){
			LogUtil.write(ERROR_LEVEL, "Request execution failed due to " + e.getMessage() + " {}", e.getCause());
			throw e;
		}
		finally{
			long endTime = System.currentTimeMillis();
			LogUtil.write(loggingLevel, method.getName() + "() finished execution and took " + (endTime - startTime) + " millis time to execute");		
		}		
		
		if(result != null){
			LogUtil.write(loggingLevel, method.getName() + "() Respone: [ " + result + " ]");
		}
		
		return result;
	}
	
	private void logParams(ProceedingJoinPoint joinPoint, Method method, String logLevel){
		if(joinPoint.getArgs() != null && joinPoint.getArgs().length > 0){
			StringBuilder sb = new StringBuilder();
			sb.append(method.getName() + "() params [");
			for(int i = 0; i < joinPoint.getArgs().length; i++){
				sb.append(method.getParameterTypes()[i].getName() + ":" + joinPoint.getArgs()[i]);
				if(i < joinPoint.getArgs().length -1)
					sb.append(", ");
			}
			sb.append("]");
			LogUtil.write(logLevel, sb.toString());
		}
	}
}
