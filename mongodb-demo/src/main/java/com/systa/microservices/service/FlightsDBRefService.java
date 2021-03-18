package com.systa.microservices.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systa.microservices.documents.FlightInformationV2;
import com.systa.microservices.repositories.FlightInformationV2Repository;

@Service
public class FlightsDBRefService {
	
	
	@Autowired
	FlightInformationV2Repository repository;
	
	public List<FlightInformationV2> findAllFlights(){
		return repository.findAll();
	}
}
