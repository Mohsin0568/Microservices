package com.systa.microservices.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document("airports")
@Data
public class Airport {
    @Id
    private String id;
    private String name;
    private String city;
    private int passengersServed;

    public Airport(String id, String name, String city, int passengersServed) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.passengersServed = passengersServed;
    }

}
