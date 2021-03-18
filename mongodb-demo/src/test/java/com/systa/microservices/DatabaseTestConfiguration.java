package com.systa.microservices;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.systa.microservices.listeners.GenericCascadeListener;

@TestConfiguration
public class DatabaseTestConfiguration {
	
	@Bean
    GenericCascadeListener genericCascadeListener(MongoTemplate mongoTemplate){
        return new GenericCascadeListener();
    }
}
