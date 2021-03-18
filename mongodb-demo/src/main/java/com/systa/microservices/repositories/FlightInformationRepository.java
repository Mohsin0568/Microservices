package com.systa.microservices.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.systa.microservices.documents.FlightInformation;

@Repository
public interface FlightInformationRepository 
	extends MongoRepository<FlightInformation, String> {

	public List<FlightInformation> findByDepartureCityAndDestinationCity(
			String departureCity, String destinationCity);
	
	@Query("{'aircraft.nbSeats' : {$gte: ?0}}")
	public List<FlightInformation> findByMinAircraftNbSeats(int minNbSeats);
}
