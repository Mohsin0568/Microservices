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
import com.systa.microservices.service.FlightInformationService;
import com.systa.microservices.service.FlightsService;

/**
 * @author mohsin
 *
 */

@RestController
public class BasicController {
	
	@Autowired
	FlightsService flightsService;
	
	@Autowired
	FlightInformationService flightInformationService;

	
//	localhost:8881/flights/markAllFlightToRomeAsDelayed
//	use below query to check the results in database.
//	db.flights.find({"destination":"Rome"}).pretty()
	@GetMapping("/markAllFlightToRomeAsDelayed")
	public void markAllFlightToRomeAsDelayed() {
		flightsService.markAllFlightToRomeAsDelayed();
	}
	
	
//	localhost:8881/flights/removeFlightWithDurationLessThanTwoHours
//	use below query to check the results in database.
//	db.flights.find({"durationMin": {$lt: 120}}).pretty()
	@GetMapping("/removeFlightWithDurationLessThanTwoHours")
	public void removeFlightWithDurationLessThanTwoHours() {
		flightsService.removeFlightWithDurationLessThanTwoHours();
	}
	
//	localhost:8881/flights/updateNbSeats
//	use below query to check the results in database.
//	db.flights.find({"aircraft.model": "A319"}).pretty()
	@GetMapping("/updateNbSeats")
	public void updateNbSeats() {
		flightsService.updateNbSeats();
	}
	
	
//	localhost:8881/flights/getFlightInformation?pageNo=0&pageSize=3&sortyBy=departure
	@GetMapping("/getFlightInformation")
	public List<FlightInformation> getFlightInformation(@RequestParam(name = "pageNo") int pageNo,
			@RequestParam(name = "pageSize") int pageSize,
			@RequestParam(name = "sortyBy") String sortyBy){
		return flightInformationService.getAllFlights(sortyBy, pageNo, pageSize);
	}
	
//	localhost:8881/flights/countInternations
//	Verify the results using below query
//	db.flights.find({"type":"International"}).size()
	@GetMapping("/countInternations")
	public long countInternations() {
		return flightInformationService.countInternations();
	}
	
//	localhost:8881/flights/findAllFlightsByDepartureCity?departureCity=Madrid
	@GetMapping("/findAllFlightsByDepartureCity")
	public List<FlightInformation> findAllFlightsByDepartureCity(@RequestParam(name = "departureCity") String departureCity) {
		return flightInformationService.findAllFlightsByDepartureCity(departureCity);
	}
	
//	localhost:8881/flights/findAllFlightsByDuration?min=110&max=120
	@GetMapping("/findAllFlightsByDuration")
	public List<FlightInformation> findAllFlightsByDuration(@RequestParam(name = "min") int min,
			@RequestParam(name = "max") int max) {
		return flightInformationService.findAllFlightsByDuration(min, max);
	}
	
//	localhost:8881/flights/findAllFlightsDelayedAtParticularDeparture?departureCity=Madrid
//	localhost:8881/flights/findAllFlightsDelayedAtParticularDeparture?departureCity=Las%20Vegas
	@GetMapping("/findAllFlightsDelayedAtParticularDeparture")
	public List<FlightInformation> findAllFlightsDelayedAtParticularDeparture(
			@RequestParam(name = "departureCity") String departureCity) {
		return flightInformationService.findAllFlightsDelayedAtParticularDeparture(departureCity);
	}
	
//	localhost:8881/flights/findAllFlightsAreOnTimeByCity?city=Madrid
	@GetMapping("/findAllFlightsAreOnTimeByCity")
	public List<FlightInformation> findAllFlightsAreOnTimeByCity(
			@RequestParam(name = "city") String city) {
		return flightInformationService.findAllFlightsAreOnTimeByCity(city);
	}
	
//	localhost:8881/flights/findByAircraftModel?model=A319
	@GetMapping("/findByAircraftModel")
	public List<FlightInformation> findByAircraftModel(
			@RequestParam(name = "model") String model) {
		return flightInformationService.findByAircraftModel(model);
	}
	
//	localhost:8881/flights/searchByText?text=Rome
//	NOt working
	@GetMapping("/searchByText")
	public List<FlightInformation> searchByText(
			@RequestParam(name = "text") String text) {
		return flightInformationService.searchByText(text);
	}
	
}
