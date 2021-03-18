package com.systa.microservices.util;

import java.util.List;

import com.systa.microservices.documents.FlightInformationV2;

public class FlightPrinterV2 {
	
	public static void print(List<FlightInformationV2> flights){
        String header = String.format("%-30s %-30s %-8s %-13s %-9s %-8s",
                "DEP","DST","DURATION","DATE","DELAYED","A. TYPE");
        System.out.println(header);
        for (FlightInformationV2 f: flights) {
            String data = String.format("%-30s %-30s %-8s %-13s %-9s %-8s",
                    f.getDeparture().getName(),
                    f.getDestination().getName(),
                    f.getDurationMin(),
                    f.getDepartureDate(),
                    f.isDelayed(),
                    f.getAircraft().getModel());
            System.out.println(data);
        }
        System.out.println();
    }

}
