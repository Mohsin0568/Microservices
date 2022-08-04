package com.systa.kafka.libraryeventsconsumer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.systa.kafka.libraryeventsconsumer.entity.LibraryEvent;

@Repository
public interface LibraryEventRepository extends CrudRepository<LibraryEvent, Integer>{
    
}
