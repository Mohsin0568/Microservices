package com.systa.kafka.libraryeventsconsumer.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.systa.kafka.libraryeventsconsumer.service.LibraryEventService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LibraryEventsRetryConsumer {

    @Autowired
    LibraryEventService service;
    
    @KafkaListener(topics = {"${topics.retry}"}, 
        groupId = "retry-listener-group",
        autoStartup = "${retry.topic.startup:true}")
    public void onMessage(ConsumerRecord<Integer, String> record) throws JsonMappingException, JsonProcessingException{
        log.info("Consumer records is {}", record);
        service.processLibraryEvent(record);
    }
    
}
