package com.systa.microservices.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.systa.microservices.documents.Airport;

@Repository
public interface AirportRepository extends MongoRepository<Airport, String> {

}
