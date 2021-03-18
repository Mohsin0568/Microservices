package com.systa.microservices;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.systa.microservices.documents.Aircraft;
import com.systa.microservices.documents.Airport;
import com.systa.microservices.documents.FlightInformation;
import com.systa.microservices.documents.FlightInformationV2;
import com.systa.microservices.documents.FlightType;
import com.systa.microservices.repositories.AirportRepository;
import com.systa.microservices.repositories.FlightInformationV2Repository;
import com.systa.microservices.util.FlightPrinterV2;

//@Component
public class DatabaseSeederRunnerUsingReferences implements CommandLineRunner{
	
	@Autowired
	private FlightInformationV2Repository flightRepository;
	
	@Autowired
	private AirportRepository airportRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
    public void run(String... args) {
        empty();
        seed();
    }
	
	private void seed() {
        // Airports
        Airport rome = new Airport(
                "1d1aab22-670b-48cb-a027-721e2055731f",
                "Leonardo da Vinci",
                "Rome",
                42995119);
        Airport paris = new Airport(
                "d04a8c26-7527-407c-81ef-680e5de34cea",
                "Charles de Gaulle",
                "Paris",
                72229723);
        Airport copenhagen = new Airport(
                "7ed990d2-6471-485d-931e-c77729dc0f25",
                "Copenhagen Airport",
                "Copenhagen",
                30298531);

        // Flight data
        FlightInformationV2 flightOne = new FlightInformationV2();
        flightOne.setId("b8b50563-ca90-4afc-9d43-b674892a525c");
        flightOne.setDelayed(false);
        flightOne.setDeparture(rome);
        flightOne.setDestination(paris);
        flightOne.setDepartureDate(LocalDate.of(2019, 3, 12));
        flightOne.setType(FlightType.International);
        flightOne.setDurationMin(80);
        flightOne.setAircraft(new Aircraft("737", 180));

        FlightInformationV2 flightTwo = new FlightInformationV2();
        flightTwo.setId("c0725fbb-eebb-4aae-8b41-3887793d0c50");
        flightTwo.setDelayed(false);
        flightTwo.setDeparture(paris);
        flightTwo.setDestination(copenhagen);
        flightTwo.setDepartureDate(LocalDate.of(2019, 5, 11));
        flightTwo.setType(FlightType.International);
        flightTwo.setDurationMin(600);
        flightTwo.setAircraft(new Aircraft("747", 300));

        // Seed
       
        // cascading on save is not supported by default for references in spring.
        // we need to insert child elements manually and then insert parent element.
        // cascading can be acheived by hooking into life cycle events.
        // life cycle events are defined in class GenericCascadeListener which will insert child objects when parent object is saved.

        // List<Airport> airports = Arrays.asList(rome, paris, copenhagen);
        // airportRepository.insert(airports);


        List<FlightInformationV2> flights = Arrays.asList(flightOne, flightTwo);
        flightRepository.insert(flights);

        // Print
        FlightPrinterV2.print(flights);
    }


    private void empty() {
        mongoTemplate.dropCollection(Airport.class);
        mongoTemplate.dropCollection(FlightInformation.class);
    }

}
