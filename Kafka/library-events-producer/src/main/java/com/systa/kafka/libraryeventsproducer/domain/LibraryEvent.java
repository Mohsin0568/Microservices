package com.systa.kafka.libraryeventsproducer.domain;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LibraryEvent {
    
    private Integer libraryEventId;
    
    @NotNull
    @Valid
    private Book book;
    private LibraryEventType libraryEventType;
}
