/**
 * 
 */
package com.systa.microservices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systa.microservices.documents.FlightInformation;
import com.systa.microservices.service.FlightRepoService;

/**
 * @author mohsin
 *
 */

@RestController
public class RepositoryController {
	
	@Autowired
	FlightRepoService service;
	
//	localhost:8881/flights/getFlightById?id=c0725fbb-eebb-4aae-8b41-3887793d0c50
//	use below query to check the results in database.
//	db.flights.find({"_id":"c0725fbb-eebb-4aae-8b41-3887793d0c50"}).pretty()
	@GetMapping("/getFlightById")
	public FlightInformation getFlightById(@RequestParam(name = "id") String id) {
		return service.getFlightById(id);
	}
	

//	localhost:8881/flights/delayFlight?id=c0725fbb-eebb-4aae-8b41-3887793d0c50&duration=50
//	use below query to check the results in database.
//	db.flights.find({"_id":"c0725fbb-eebb-4aae-8b41-3887793d0c50"}).pretty()
	@GetMapping("/delayFlight")
	public void delayFlight(@RequestParam(name = "id") String id,
			@RequestParam(name = "duration") int duration) {
		service.delayFlight(id, duration);
	}
	
//	localhost:8881/flights/removeFlight?id=c0725fbb-eebb-4aae-8b41-3887793d0c50
//	use below query to check the results in database.
//	db.flights.find({"_id":"c0725fbb-eebb-4aae-8b41-3887793d0c50"}).pretty()
	@GetMapping("/removeFlight")
	public void removeFlight(@RequestParam(name = "id") String id) {
		service.removeFlight(id);
	}	
	
//	localhost:8881/flights/getFlightByDepartureandDestination?departure=Bucharest&destination=Rome
//	use below query to check the results in database.
//	db.flights.find({"departure":"Bucharest", "destination":"Rome"}).pretty()
	@GetMapping("/getFlightByDepartureandDestination")
	public List<FlightInformation> getFlightByDepartureandDestination(@RequestParam(name = "departure") String departure,
			@RequestParam(name = "destination") String destination) {
		return service.getFlightByDepartureandDestination(departure, destination);
	}
	
//	localhost:8881/flights/findByMinimumNumberOfSeats?minimumSeats=200
//	use below query to check the results in database.
//	db.flights.find({"aircraft.nbSeats":{$gte:200}}).pretty()
	@GetMapping("/findByMinimumNumberOfSeats")
	public List<FlightInformation> findByMinimumNumberOfSeats(@RequestParam(name = "minimumSeats") int minimumSeats) {
		return service.findByMinimumNumberOfSeats(minimumSeats);
	}
}
