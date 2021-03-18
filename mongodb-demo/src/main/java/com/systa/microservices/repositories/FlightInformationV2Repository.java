package com.systa.microservices.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.systa.microservices.documents.FlightInformationV2;

@Repository
public interface FlightInformationV2Repository extends MongoRepository<FlightInformationV2, String>{

	
	@Query("{'aircraft.nbSeats' : {$gte: ?0}}")
    List<FlightInformationV2> findByMinAircraftNbSeats(int minNbSeats);
}
