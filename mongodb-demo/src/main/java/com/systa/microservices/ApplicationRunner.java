package com.systa.microservices;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.systa.microservices.documents.FlightInformation;

/*
In Spring Boot, a class that implements CommandLineRunner
is executed after the application is bootstrapped
 */

@Component
public class ApplicationRunner implements CommandLineRunner {

    private MongoTemplate mongoTemplate;

    public ApplicationRunner(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(String... args) throws Exception {

//    	Query all = new Query(); // deleting all records before insert.    	
//    	mongoTemplate.findAllAndRemove(all, FlightInformation.class);
    	
    	// deleting all records before insert.
//    	mongoTemplate.dropCollection(FlightInformation.class);
//    	
//        FlightInformation emptyFlight = new FlightInformation();
//        this.mongoTemplate.insert(emptyFlight);
        
        System.out.println("Application started...");
    }
}
