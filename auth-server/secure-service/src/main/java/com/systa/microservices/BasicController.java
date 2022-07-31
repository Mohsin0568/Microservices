package com.systa.microservices;

import java.util.ArrayList;

import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {
	
	@PostMapping(value = "/test", consumes = "application/json")	
	public String testService(@RequestBody TollUsage data) {
		System.out.println(data);
		return "Successfull";
	}
	
	@PostMapping(value = "/test", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public String testService2(@ModelAttribute TollUsage data) {
		System.out.println("Form data called " + data);
		return "successfull";
	}
	
	
	@RequestMapping("/tolldata")
	public ArrayList<TollUsage> getTollData() {
		
//		OAuth2Authentication auth = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
//		
//		System.out.println(auth.getUserAuthentication().getDetails());
		
//		System.out.println(auth.getDetails());
//		
//		System.out.println(auth.getName());
		
		System.out.println("From session " + SessionContext.getSessionDetail());
		
		TollUsage instance1 = new TollUsage("200", "station150", "B65GT1W", "2016-09-30T06:31:22");
		TollUsage instance2 = new TollUsage("201", "station119", "AHY673B", "2016-09-30T06:32:50");
		TollUsage instance3 = new TollUsage("202", "station150", "ZN2GP0", "2016-09-30T06:37:01");
		
		ArrayList<TollUsage> tolls = new ArrayList<TollUsage>();
		tolls.add(instance1);
		tolls.add(instance2);
		tolls.add(instance3);
		
		return tolls;
	}
	
}
