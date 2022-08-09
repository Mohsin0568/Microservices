package com.systa.kafka.libraryeventsconsumer.config;

import java.util.List;

import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableKafka
@Slf4j
public class LibraryEventsConsumerConfig {

    public DefaultErrorHandler errorHandler(){

        // Adding exception in below list for which we don't want to retry consumer logic,
        // for below exceptions, kafka will not do retry if there is an exception in consumer
        var exceptionsToIgnore = List.of(
            IllegalArgumentException.class
        );

        var fixedBackOff = new FixedBackOff(1000L, 2);
        var handler = new DefaultErrorHandler(fixedBackOff);

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
