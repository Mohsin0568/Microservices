package com.systa.kafka.libraryeventsproducer.producer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.systa.kafka.libraryeventsproducer.domain.LibraryEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LibraryEventsProducer {
    
    @Autowired
    KafkaTemplate<Integer, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    // this is one of the approach which will send message to kafka topic asynchonously.
    public void sendLibraryEvent(LibraryEvent event) throws JsonProcessingException{
        Integer key = event.getLibraryEventId();
        String value = objectMapper.writeValueAsString(event);

        ListenableFuture<SendResult<Integer, String>> listenableFuture 
            = kafkaTemplate.send("library-events", key, value);
            
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
                
            }
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key, ex);
            }            
        });
    }

    // this is one of the approach which will send message to kafka topic synchonously.
    public void sendLibraryEventSynchronous(LibraryEvent event) throws JsonProcessingException{

        Integer key = event.getLibraryEventId();
        String value = objectMapper.writeValueAsString(event);

        try {
            SendResult<Integer, String> result = 
                kafkaTemplate.send("library-events", key, value).get();

            handleSuccess(key, value, result);
        } catch (InterruptedException | ExecutionException e) {
            handleFailure(key, e);
        }
    }

    // this is one of the approach which will send message to kafka topic using ProducerRecord. This approach is used from controller
    public void sendLibraryEventViaProducerRecord(LibraryEvent event) throws JsonProcessingException{
        Integer key = event.getLibraryEventId();
        String value = objectMapper.writeValueAsString(event);

        ProducerRecord<Integer, String> record = buildProducerRecord(key, value, "library-events");

        ListenableFuture<SendResult<Integer, String>> listenableFuture 
            = kafkaTemplate.send(record);
            
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
                
            }
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key, ex);
            }            
        });
    }


    private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value, String topic) {

        List<Header> headers = List.of(new RecordHeader("event-source", "scanner".getBytes()));

        return new ProducerRecord<Integer, String>(topic, null, key, value, headers);
    }   

    private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
        log.info("Message send successfully for the key {} with value {} and partition is {}", 
            key, value, result.getRecordMetadata().partition());
    }

    private void handleFailure(Integer key, Throwable ex) {
        log.error("Failed to send message with key {}, error is {}", key, ex.getMessage());
    }
}
