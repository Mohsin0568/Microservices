package com.systa.microservices.logger;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class LogUtil {
	
	public static void write(String logLevel, String message){
		
		switch(logLevel){
		case "DEBUG":
			log.debug(message);
			break;
			
		case "debug":
			log.debug(message);
			break;
			
		case "INFO":
			log.info(message);
			break;
			
		case "info":
			log.info(message);
			break;
			
		default:
			log.warn("No suitable log level found");
			break;
		}
	}
	
	public static void write(String logLevel, String message, Throwable th){
		switch(logLevel){
		
		case "ERROR":
			log.error(message, th);
			break;
			
		case "error":
			log.error(message, th);
			break;
			
		default:
			log.warn("No suitable log level found");
			break;
		}
	}

}
