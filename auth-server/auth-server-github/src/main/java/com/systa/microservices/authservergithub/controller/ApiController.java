package com.systa.microservices.authservergithub.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApiController {
	
	@Autowired
	private OAuth2ClientContext clientContext;
	
	@Autowired
    private OAuth2RestTemplate oauth2RestTemplate;

	@RequestMapping("/")
	public String homePage(){
		return "home";
	}
	
	@GetMapping("/reports")
	public String reports(Model model){
		
		/*
		 * use below code to print token given by github (authsever). */
		OAuth2AccessToken token = clientContext.getAccessToken();
		System.out.println("Token: " + token.getValue());
		
		ResponseEntity<ArrayList<TollUsage>> tolls = oauth2RestTemplate.exchange("http://localhost:8881/services/tolldata", HttpMethod.GET, null, new ParameterizedTypeReference<ArrayList<TollUsage>>(){});
		
		model.addAttribute("tolls", tolls.getBody());
		return "reports";
	}
	
	public static class TollUsage {
		
		public String Id;
		public String stationId;
		public String licensePlate;
		public String timestamp;
		
		public TollUsage() {}
		
		public TollUsage(String id, String stationid, String licenseplate, String timestamp){
			this.Id = id;
			this.stationId = stationid;
			this.licensePlate = licenseplate;
			this.timestamp = timestamp;
		}
		
		
	}
}
