package com.systa.kafka.libraryeventsconsumer.config;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.springframework.util.backoff.FixedBackOff;

import com.systa.kafka.libraryeventsconsumer.service.FailureRecordService;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableKafka
@Slf4j
public class LibraryEventsConsumerConfig {

    @Value("${topics.retry}")
    private String retryTopic;

    @Value("${topics.dlt}")
    private String deadLetterTopic;

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Autowired
    FailureRecordService failureRecordService;

    // this method will return the recover logic, this recoverer logic will execute once all reattempts are exhausted.
    public DeadLetterPublishingRecoverer publisherRecover(){
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
            (r, e) -> {
                if (e.getCause() instanceof RecoverableDataAccessException) {
                    return new TopicPartition(retryTopic, r.partition());
                }
                else {
                    return new TopicPartition(deadLetterTopic, r.partition());
                }
            });
        return recoverer;
    }

    ConsumerRecordRecoverer consumerRecordRecoverer = (consumer, e) -> {
        ConsumerRecord<Integer, String> record = (ConsumerRecord<Integer, String>) consumer;
        if (e.getCause() instanceof RecoverableDataAccessException) {
            
            failureRecordService.saveFailureRecord(record, e, "RETRY");
        }
        else {
            failureRecordService.saveFailureRecord(record, e, "DEAD");
        }
    };

    public DefaultErrorHandler errorHandler(){

        // Adding exception in below list for which we don't want to retry consumer logic,
        // for below exceptions, kafka will not do retry if there is an exception in consumer
        var exceptionsToIgnore = List.of(
            IllegalArgumentException.class
        );

        var exponentialBackOff = new ExponentialBackOffWithMaxRetries(2);
        exponentialBackOff.setInitialInterval(1000l);
        exponentialBackOff.setMultiplier(2.0);
        exponentialBackOff.setMaxInterval(2000l);

        // FixedBackOff will make sure that reattempts will happen only 2 times and every attempt will have 1 sec time gap.
        var fixedBackOff = new FixedBackOff(1000L, 2);
        var handler = new DefaultErrorHandler(
            //publisherRecover(), // this line will send fail record to another kafka topic
            consumerRecordRecoverer, // this line will send fail record to DB.
            //fixedBackOff // commenting fixedbackoff ot test exponential back off
            exponentialBackOff
        );

        exceptionsToIgnore.forEach(handler :: addNotRetryableExceptions);

        handler.setRetryListeners((record, ex, deliveryAttempt) -> {
            log.info("Failed record in retry listener, exception is {}, and delivery attempt is {}",
                ex.getMessage(), deliveryAttempt);
        });
        return handler;
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
        ConcurrentKafkaListenerContainerFactoryConfigurer config,
        ConsumerFactory<Object, Object> kafkaConsumerFactory){

        ConcurrentKafkaListenerContainerFactory<Object, Object> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        config.configure(containerFactory, kafkaConsumerFactory);
        containerFactory.setConcurrency(3);
        containerFactory.setCommonErrorHandler(errorHandler());
        return containerFactory;
    } 
}
