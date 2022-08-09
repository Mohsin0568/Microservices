package com.systa.kafka.libraryeventsconsumer.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systa.kafka.libraryeventsconsumer.entity.FailureRecord;
import com.systa.kafka.libraryeventsconsumer.repository.FailureRecordRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FailureRecordService {
    
    @Autowired
    private FailureRecordRepository failureRecordRepository;

    public void saveFailureRecord(ConsumerRecord<Integer, String> consumer, Exception e, String failureType){
        log.info("Going to persist failed record in db of type {}", failureType);

        FailureRecord record = FailureRecord.builder()
            .id(null)
            .topic(consumer.topic())
            .errorRecord(consumer.value())
            .recordKey(consumer.key())
            .partition(consumer.partition())
            .recordOffset(consumer.offset())
            .exception(e.getCause().getMessage())
            .status(failureType).build();

        failureRecordRepository.save(record);

    }
}
