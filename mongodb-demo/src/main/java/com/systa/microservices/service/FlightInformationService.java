/**
 * 
 */
package com.systa.microservices.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

import com.systa.microservices.documents.FlightInformation;
import com.systa.microservices.documents.FlightType;

/**
 * @author mohsin
 *
 */

@Service
public class FlightInformationService {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	/* 
	 * TODO: implement following functions.
	 * 
	 * 1) Find all flights by paging and sorting.
	 * 2) Find by flight id.
	 * 3) Count all international flights.
	 * 4) Find all flights by departure city.
	 * 5) Find all flights by duration between min and max
	 * 6) Find all flights delayed at a particular departure city
	 * 7) Find all flights that are on time and relate to a city
	 * 8) Find by aircraft model 
	 * 9) Find by free text search
	 * 
	 * */
	
	
//	1) Find all flights by paging and sorting.
	public List<FlightInformation> getAllFlights(String field, int pageNo, int pageSize){
		
		Query query = new Query()
				.with(Sort.by(Sort.Direction.ASC, field))
				.with(PageRequest.of(pageNo, pageSize));
				
		
		return mongoTemplate.find(query, FlightInformation.class);
	}
	
	
//	2) Find by flight id.
	public FlightInformation getFlightById(String id) {
		return mongoTemplate.findById(id, FlightInformation.class);
	}
	
//	3) Count all international flights.
	public long countInternations() {
		
		Query query = Query.query(
				Criteria.where("type").is(FlightType.International)
				);
		
		return mongoTemplate.count(query, FlightInformation.class);
	}
	
//	4) Find all flights by departure city.
	public List<FlightInformation> findAllFlightsByDepartureCity(String departureCity){
		
		Query query = Query.query(
				Criteria.where("departure").is(departureCity)
				);
		
		return mongoTemplate.find(query, FlightInformation.class);
	}
	
//	5) Find all flights by duration between min and max
	public List<FlightInformation> findAllFlightsByDuration(int min, int max){
		
		Query query = Query.query(
				Criteria.where("durationMin")
				.gte(min)
				.lte(max)
				).with(Sort.by(Sort.Direction.ASC, "durationMin"));
		
		return mongoTemplate.find(query, FlightInformation.class);
	}
	
//	6) Find all flights delayed at a particular departure city
	public List<FlightInformation> findAllFlightsDelayedAtParticularDeparture(String departureCity){
		
		Query query = Query.query(
				Criteria.where("departure").is(departureCity)
				.and("isDelayed").is(true)
				);
		
		return mongoTemplate.find(query, FlightInformation.class);
	}
	
//	7) Find all flights that are on time and relate to a city
	public List<FlightInformation> findAllFlightsAreOnTimeByCity(String city){
		Query query = Query.query(
				new Criteria()
					.orOperator(
								Criteria.where("departure").is(city),
								Criteria.where("destination").is(city)
							)
					.andOperator(Criteria.where("isDelayed").is(false))
				);
		
		return mongoTemplate.find(query, FlightInformation.class);
	}
	
//	8) Find by aircraft model 
	public List<FlightInformation> findByAircraftModel(String model){
		Query query = Query.query(
					Criteria.where("aircraft.model").is(model)
				);
		
		return mongoTemplate.find(query, FlightInformation.class);
	}
	
//	9) Find by free text search
	public List<FlightInformation> searchByText(String text){
		
		TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matching(text);
		
		Query query = TextQuery.queryText(textCriteria)
				.sortByScore()
				.with(PageRequest.of(0, 3));
		
		return mongoTemplate.find(query, FlightInformation.class);
		
	}

}
