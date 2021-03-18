package com.systa.microservices.documents;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Document("flights")
@Data
public class FlightInformation {
    @Id
    private String id;

    @Field("departure")
    @TextIndexed
    private String departureCity;

    @Field("destination")
    @TextIndexed
    private String destinationCity;

    @TextIndexed(weight = 2)
    private String description;
    
    private FlightType type;
    private boolean isDelayed;
    private int durationMin;
    private LocalDate departureDate;
    private Aircraft aircraft;    

    @Transient
    private LocalDate createdAt;

    public FlightInformation(){
        this.createdAt = LocalDate.now();
    }   
}
