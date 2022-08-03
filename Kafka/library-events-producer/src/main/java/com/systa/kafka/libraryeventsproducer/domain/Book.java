package com.systa.kafka.libraryeventsproducer.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @NotNull
    private Integer bookId;
    
    @NotBlank
    private String bookName;
    
    @NotBlank
    private String bookAuthor;
    
}
