package com.systa.kafka.libraryeventsproducer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.systa.kafka.libraryeventsproducer.domain.LibraryEvent;
import com.systa.kafka.libraryeventsproducer.domain.LibraryEventType;
import com.systa.kafka.libraryeventsproducer.producer.LibraryEventsProducer;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class LibraryEventsController {

    @Autowired
    LibraryEventsProducer producer;
    
    @PostMapping("/v1/libraryEvent")
    public ResponseEntity<LibraryEvent> postLibraryEvent(@Valid @RequestBody LibraryEvent event) throws JsonProcessingException{

        // invoke kafka producer
        event.setLibraryEventType(LibraryEventType.NEW);
        //producer.sendLibraryEvent(event);
        log.info("Before calling producer");
        producer.sendLibraryEventViaProducerRecord(event);
        log.info("after calling producer");
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }

    @PutMapping("/v1/libraryEvent")
    public ResponseEntity<?> putLibraryEvent(@Valid @RequestBody LibraryEvent event) throws JsonProcessingException{

        if(event.getLibraryEventId() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("EventId cannot be null");
        }
        
        event.setLibraryEventType(LibraryEventType.UPDATE);
        log.info("Before calling producer");
        producer.sendLibraryEventViaProducerRecord(event);
        log.info("after calling producer");
        return ResponseEntity.status(HttpStatus.OK).body(event);
    }



    // @PostMapping("/v1/libraryEvent/synchronous")
    // public ResponseEntity<LibraryEvent> postLibraryEventSynchronous(@RequestBody LibraryEvent event) throws JsonProcessingException{

    //     // invoke kafka producer
    //     log.info("Before calling producer");
    //     producer.sendLibraryEventSynchronous(event);
    //     log.info("after calling producer");
    //     return ResponseEntity.status(HttpStatus.CREATED).body(event);
    // }

    // @PostMapping("/v1/libraryEvent/producerRecord")
    // public ResponseEntity<LibraryEvent> postLibraryEventViaProducerRecord(@RequestBody LibraryEvent event) throws JsonProcessingException{

    //     // invoke kafka producer
    //     log.info("Before calling producer");
    //     producer.sendLibraryEventViaProducerRecord(event);
    //     log.info("after calling producer");
    //     return ResponseEntity.status(HttpStatus.CREATED).body(event);
    // }
}
