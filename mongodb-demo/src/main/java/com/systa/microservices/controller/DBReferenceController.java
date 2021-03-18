package com.systa.microservices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.systa.microservices.documents.FlightInformationV2;
import com.systa.microservices.service.FlightsDBRefService;

@RestController
public class DBReferenceController {
	
	@Autowired
	FlightsDBRefService service;

//	localhost:8881/flights/getAllFlights
	@GetMapping("/getAllFlights")
	public List<FlightInformationV2> getAllFlights(){
		return service.findAllFlights();
	}
}
