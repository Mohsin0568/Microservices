package com.systa.kafka.libraryeventsproducer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;

import com.systa.kafka.libraryeventsproducer.domain.Book;
import com.systa.kafka.libraryeventsproducer.domain.LibraryEvent;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(topics = {"library-events"}, partitions = 3)
@TestPropertySource(properties = {"spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}", 
    "spring.kafka.admin.properties.bootstrap.servers=${spring.embedded.kafka.brokers}"})
public class LibraryEventsControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    EmbeddedKafkaBroker kafkaBroker;

    private Consumer<Integer, String> consumer;

    @BeforeEach
    public void setUp(){

        Map<String, Object> configs = KafkaTestUtils.consumerProps("group-1", "true", kafkaBroker);
        consumer = new DefaultKafkaConsumerFactory<>
            (configs, new IntegerDeserializer(), new StringDeserializer()).createConsumer();
        kafkaBroker.consumeFromAllEmbeddedTopics(consumer);
    }

    @AfterEach
    public void tearDown(){
        consumer.close();
    }

    @Test
    @Timeout(5)
    public void testPostLibraryEvent(){

        Book book = Book.builder().bookAuthor("Mohsin")
            .bookId(1)
            .bookName("learn spring kafka")
            .build();

        LibraryEvent event = LibraryEvent.builder()
            .book(book)
            .libraryEventId(null)
            .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        
        HttpEntity<LibraryEvent> entity = new HttpEntity<>(event, headers);

        ResponseEntity<LibraryEvent> responseEntity = 
            restTemplate.exchange("/v1/libraryEvent", HttpMethod.POST, entity, LibraryEvent.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        ConsumerRecord<Integer, String> record = KafkaTestUtils.getSingleRecord(consumer, "library-events");

        String expectedRecord = "{\"libraryEventId\":null,\"book\":{\"bookId\":1,\"bookName\":\"learn spring kafka\",\"bookAuthor\":\"Mohsin\"},\"libraryEventType\":\"NEW\"}";
        assertEquals(expectedRecord, record.value());
    }
}
