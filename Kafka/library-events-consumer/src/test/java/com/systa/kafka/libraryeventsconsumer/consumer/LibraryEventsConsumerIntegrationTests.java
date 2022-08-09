package com.systa.kafka.libraryeventsconsumer.consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.systa.kafka.libraryeventsconsumer.entity.Book;
import com.systa.kafka.libraryeventsconsumer.entity.LibraryEvent;
import com.systa.kafka.libraryeventsconsumer.entity.LibraryEventType;
import com.systa.kafka.libraryeventsconsumer.repository.LibraryEventRepository;
import com.systa.kafka.libraryeventsconsumer.service.LibraryEventService;

@SpringBootTest
@EmbeddedKafka(topics = {"library-events", "library-events.RETRY", "library-events.DLT"}, partitions = 3)
@TestPropertySource(properties = {"spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}", 
    "spring.kafka.consumer.bootstrap-servers=${spring.embedded.kafka.brokers}"})
public class LibraryEventsConsumerIntegrationTests {

    @Autowired
    EmbeddedKafkaBroker kafkaBroker;

    @Autowired
    KafkaTemplate<Integer, String> kafkaTemplate;

    @Autowired
    KafkaListenerEndpointRegistry listnerEndpointRegistry;

    @SpyBean
    LibraryEventsConsumer libraryEventConsumerSpy;

    @SpyBean
    LibraryEventService libraryEventServiceSpy;

    @Autowired
    LibraryEventRepository libraryEventRepository;

    @Autowired
    ObjectMapper objectMapper;

    private Consumer<Integer, String> consumer;

    @Value("${topics.retry}")
    private String retryTopic;

    @Value("${topics.dlt}")
    private String deadLetterTopic;

    @BeforeEach
    void setUp(){

        for(MessageListenerContainer container: listnerEndpointRegistry.getListenerContainers()){
            ContainerTestUtils.waitForAssignment(container, kafkaBroker.getPartitionsPerTopic());
        }

    }

    @AfterEach
    void tearDown(){
        libraryEventRepository.deleteAll();
    }

    @Test
    void testonMessage_NewEvent() throws InterruptedException, ExecutionException, JsonMappingException, JsonProcessingException{
        // given
        String json = " {\"libraryEventId\":null,\"libraryEventType\":\"NEW\",\"book\":{\"bookId\":456,\"bookName\":\"Kafka Using Spring Boot\",\"bookAuthor\":\"Mohsin\"}}";
        kafkaTemplate.sendDefault(json).get();

        // when
        CountDownLatch latch = new CountDownLatch(1);
        latch.await(3, TimeUnit.SECONDS);

        // then
        verify(libraryEventConsumerSpy, times(1)).onMessage(isA(ConsumerRecord.class));
        verify(libraryEventServiceSpy, times(1)).processLibraryEvent(isA(ConsumerRecord.class));

        List<LibraryEvent> list = (List<LibraryEvent>) libraryEventRepository.findAll();

        assert list.size() == 1;

        list.forEach(libraryEvent -> {
            assert libraryEvent.getLibraryEventId() != null;
            assertEquals(456, libraryEvent.getBook().getBookId());
        });
    }

    @Test
    void testonMessage_UpdateEvent() throws InterruptedException, ExecutionException, JsonMappingException, JsonProcessingException{
        // given
        String json = " {\"libraryEventId\":null,\"libraryEventType\":\"UPDATE\",\"book\":{\"bookId\":456,\"bookName\":\"Kafka Using Spring Boot\",\"bookAuthor\":\"Mohsin\"}}";
        
        LibraryEvent libraryEvent = objectMapper.readValue(json, LibraryEvent.class);
        libraryEvent.getBook().setLibraryEvent(libraryEvent);
        libraryEventRepository.save(libraryEvent);
        
        Book book = Book.builder().bookAuthor("Mohsin")
            .bookName("Kafka Using Spring Boot 2.x").bookId(456).build();
        libraryEvent.setLibraryEventType(LibraryEventType.UPDATE);
        libraryEvent.setBook(book);

        String updatedJson = objectMapper.writeValueAsString(libraryEvent);        
        kafkaTemplate.sendDefault(updatedJson).get();

        // when
        CountDownLatch latch = new CountDownLatch(1);
        latch.await(3, TimeUnit.SECONDS);

        // then
        verify(libraryEventConsumerSpy, times(1)).onMessage(isA(ConsumerRecord.class));
        verify(libraryEventServiceSpy, times(1)).processLibraryEvent(isA(ConsumerRecord.class));

        LibraryEvent event = libraryEventRepository.findById(libraryEvent.getLibraryEventId()).get();
        assertEquals("Kafka Using Spring Boot 2.x", event.getBook().getBookName());
        
    }

    @Test
    void testonMessage_UpdateEvent_NullEventId() throws InterruptedException, ExecutionException, JsonMappingException, JsonProcessingException{
        // given
        String json = " {\"libraryEventId\":null,\"libraryEventType\":\"UPDATE\",\"book\":{\"bookId\":456,\"bookName\":\"Kafka Using Spring Boot\",\"bookAuthor\":\"Mohsin\"}}";
        kafkaTemplate.sendDefault(json).get();

        // when
        CountDownLatch latch = new CountDownLatch(1);
        latch.await(5, TimeUnit.SECONDS);

        // then
        verify(libraryEventConsumerSpy, times(1)).onMessage(isA(ConsumerRecord.class));
        verify(libraryEventServiceSpy, times(1)).processLibraryEvent(isA(ConsumerRecord.class));
    }

    @Test
    void testonMessage_UpdateEvent_999EventId() throws InterruptedException, ExecutionException, JsonMappingException, JsonProcessingException{
        // given
        String json = " {\"libraryEventId\":999,\"libraryEventType\":\"UPDATE\",\"book\":{\"bookId\":456,\"bookName\":\"Kafka Using Spring Boot\",\"bookAuthor\":\"Mohsin\"}}";
        kafkaTemplate.sendDefault(json).get();

        // when
        CountDownLatch latch = new CountDownLatch(1);
        latch.await(5, TimeUnit.SECONDS);

        // then
        verify(libraryEventConsumerSpy, times(3)).onMessage(isA(ConsumerRecord.class));
        verify(libraryEventServiceSpy, times(3)).processLibraryEvent(isA(ConsumerRecord.class));

        Map<String, Object> configs = KafkaTestUtils.consumerProps("group-1", "true", kafkaBroker);
        consumer = new DefaultKafkaConsumerFactory<>
            (configs, new IntegerDeserializer(), new StringDeserializer()).createConsumer();
        kafkaBroker.consumeFromAnEmbeddedTopic(consumer, retryTopic);

        ConsumerRecord<Integer, String> record = KafkaTestUtils.getSingleRecord(consumer, retryTopic);
        System.out.println(record.value());
        assertEquals(json, record.value());

    }
}
