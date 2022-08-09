package com.systa.kafka.libraryeventsconsumer.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FailureRecord {

    @Id
    @GeneratedValue
    private Integer id;

    private String topic;
    private String errorRecord;
    private Integer recordKey;
    private Integer partition;
    private Long recordOffset;
    private String exception;
    private String status;
    
}
