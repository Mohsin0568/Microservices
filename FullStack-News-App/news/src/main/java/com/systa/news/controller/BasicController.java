package com.systa.news.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/feeds")
@Log4j2
public class BasicController {

	@GetMapping("/test")
	public String basicController(){
		log.info("Basic controller is invoked");
		return "App is working on current environment";
	}
}
