package com.systa.microservices.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.systa.microservices.documents.FlightInformationV2;

@Repository
public interface FlightInformationV2Repository extends MongoRepository<FlightInformationV2, String>{

}
