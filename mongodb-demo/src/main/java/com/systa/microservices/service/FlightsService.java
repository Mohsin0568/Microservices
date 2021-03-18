package com.systa.microservices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.systa.microservices.documents.FlightInformation;

@Service
public class FlightsService {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void markAllFlightToRomeAsDelayed() {
		
		Query customQuery = Query.query(
				Criteria.where("destination").is("Rome")
				);
		
		Update setDelayed = Update.update("isDelayed", true);
		mongoTemplate.updateMulti(customQuery, setDelayed, FlightInformation.class);
		
//		use below query to check the results in database.
//		db.flights.find({"destination":"Rome"}).pretty()
	}
	
	public void removeFlightWithDurationLessThanTwoHours() {
		Query removeFlights = Query.query(
				Criteria.where("durationMin").lt(120)
				);
		
		mongoTemplate.findAllAndRemove(removeFlights, FlightInformation.class);
		
//		use below query to check the results in database.
//		db.flights.find({"durationMin": {$lt: 120}}).pretty()
	}
	
	public void updateNbSeats() {
		Query queryForModel = Query.query(
				Criteria.where("aircraft.model").is("A319")
				);
		
		Update setNbSeats = Update.update("aircraft.nbSeats", 200);		
		
		mongoTemplate.updateMulti(queryForModel, setNbSeats, FlightInformation.class);
		
//		use below query to check the results in database.
//		db.flights.find({"aircraft.model": "A319"}).pretty()
	}
}
