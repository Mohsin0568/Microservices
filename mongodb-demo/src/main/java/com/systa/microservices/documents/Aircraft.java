package com.systa.microservices.documents;

import lombok.Data;

@Data
public class Aircraft {
    private String model;
    private int nbSeats;

    public Aircraft(String model, int nbSeats) {
        this.model = model;
        this.nbSeats = nbSeats;
    }
}
