package com.systa.kafka.libraryeventsproducer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.systa.kafka.libraryeventsproducer.domain.LibraryEvent;

@RestController
public class LibraryEventsController {
    
    @PostMapping("/v1/libraryEvent")
    public ResponseEntity<LibraryEvent> postLibraryEvent(LibraryEvent event){

        // invoke kafka producer
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }
}
