package com.systa.microservices.documents;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document("flights")
@Data
public class FlightInformationV2 {

	
	@Id
    private String id;

    @Indexed(unique = true)
    private String internalId;

    @DBRef
    private Airport departure;

    @DBRef
    private Airport destination;

    private Aircraft aircraft;

    private FlightType type;
    private boolean isDelayed;
    private int durationMin;
    private LocalDate departureDate;
}
