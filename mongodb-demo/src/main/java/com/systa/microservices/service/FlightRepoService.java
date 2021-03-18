package com.systa.microservices.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systa.microservices.documents.FlightInformation;
import com.systa.microservices.repositories.FlightInformationRepository;

@Service
public class FlightRepoService {
	
	/* 
	 * TODO: implement following functions.
	 * 
	 * 1) Get Flight by Id
	 * 2) Delay Flight
	 * 3) Remove Flight
	 * 4) Get Flight by Departure and Destination
	 * 5) Get Flight by Minimumb number of seats
	 * 
	 * */
	
	@Autowired
	FlightInformationRepository repository;
	
//	1) Get Flight by Id
	public FlightInformation getFlightById(String id) {
		return repository.findById(id).get();
	}
	
//	2) Delay Flight
	public void delayFlight(String id, int duration) {
		FlightInformation flightInformation = repository.findById(id).get();
		
		flightInformation.setDurationMin(flightInformation.getDurationMin() + duration);
		repository.save(flightInformation);
	}
	
//	3) Remove Flight
	public void removeFlight(String id) {
		repository.deleteById(id);
	}
	
//	4) Get Flight by Departure and Destination
	public List<FlightInformation> getFlightByDepartureandDestination(String departure, String destination) {
		return repository.findByDepartureCityAndDestinationCity(departure, destination);
	}
	
//	5) Get Flight by Minimumb number of seats
	public List<FlightInformation> findByMinimumNumberOfSeats(int minimumSeats){
		return repository.findByMinAircraftNbSeats(minimumSeats);
	}

}
