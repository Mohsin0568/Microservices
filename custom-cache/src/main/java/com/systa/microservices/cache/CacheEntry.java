package com.systa.microservices.cache;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import lombok.Data;

@Data
public class CacheEntry<K, V> implements Cacheable<K, V> {
	
	K key;
	V value;
	long duration = 0;
	TimeUnit durationUnit = TimeUnit.SECONDS;
	LocalDateTime expiryDateTime = LocalDateTime.now();
	
	public CacheEntry(K key, V value, TimeUnit durationUnit, long duration){
		this.key = key;
		this.value = value;
		this.durationUnit = durationUnit;
		this.duration = duration;
		this.expiryDateTime = LocalDateTime.now().plusSeconds(durationUnit.toSeconds(duration));
	}

	@Override
	public boolean isExpired() {
		return LocalDateTime.now().isAfter(expiryDateTime);
	}

}
