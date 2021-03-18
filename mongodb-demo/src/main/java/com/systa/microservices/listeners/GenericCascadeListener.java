package com.systa.microservices.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Component
public class GenericCascadeListener extends AbstractMongoEventListener<Object>{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
    public void onBeforeConvert(BeforeConvertEvent event) {
        Object document = event.getSource();
        ReflectionUtils.doWithFields(document.getClass(), docField -> {
            ReflectionUtils.makeAccessible(docField);

            if (docField.isAnnotationPresent(DBRef.class)) {
                final Object fieldValue = docField.get(document);

                // Save child
                this.mongoTemplate.save(fieldValue);
            }
        });
    }
}
