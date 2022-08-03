package com.systa.kafka.libraryeventsproducer.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systa.kafka.libraryeventsproducer.domain.Book;
import com.systa.kafka.libraryeventsproducer.domain.LibraryEvent;
import com.systa.kafka.libraryeventsproducer.producer.LibraryEventsProducer;

@WebMvcTest(LibraryEventsController.class)
@AutoConfigureMockMvc
public class LibraryEventsControllerUnitTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LibraryEventsProducer producer;
    
    @Test
    void testPostLibraryEvent() throws Exception {

        Book book = Book.builder().bookAuthor("Mohsin")
            .bookId(1)
            .bookName("learn spring kafka")
            .build();

        LibraryEvent event = LibraryEvent.builder()
            .book(null)
            .libraryEventId(null)
            .build();

        String requestJson = objectMapper.writeValueAsString(event);

        doNothing().when(producer).sendLibraryEventViaProducerRecord(isA(LibraryEvent.class));

        mockMvc.perform(post("/v1/libraryEvent")
            .content(requestJson)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
        
    }
}
