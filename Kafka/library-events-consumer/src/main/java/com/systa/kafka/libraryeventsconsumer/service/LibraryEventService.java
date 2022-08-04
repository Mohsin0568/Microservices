package com.systa.kafka.libraryeventsconsumer.service;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.systa.kafka.libraryeventsconsumer.entity.LibraryEvent;
import com.systa.kafka.libraryeventsconsumer.repository.LibraryEventRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LibraryEventService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    LibraryEventRepository libraryEventRepository;
    
    public void processLibraryEvent(ConsumerRecord<Integer, String> libraryEvent) throws JsonMappingException, JsonProcessingException{
        log.info("libraryEvent : {} ", libraryEvent);

        LibraryEvent event = objectMapper.readValue(libraryEvent.value(), LibraryEvent.class);

        switch(event.getLibraryEventType()){
            case NEW: 
                save(event);
            break;

            case UPDATE:
                validate(event);
                save(event);
            break;

        }

    }

    public void save(LibraryEvent event){
        event.getBook().setLibraryEvent(event);
        libraryEventRepository.save(event);
        log.info("Successfully Persisted the libary Event {} ", event);
    }

    public void validate(LibraryEvent event){
        if(event.getLibraryEventId() == null){
            throw new IllegalArgumentException("Event id cannot be null for update event");
        }

        Optional<LibraryEvent> optionalEvent = 
            libraryEventRepository.findById(event.getLibraryEventId());

        if(!optionalEvent.isPresent()){
            throw new IllegalArgumentException("Event id is invalid for update event");
        }

        log.info("validation successfull for update event");        
    }

}
