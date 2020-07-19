package com.systa.microservices.cache;

public interface Cacheable<K, V> {

	public K getKey();
	public V getValue();
	public boolean isExpired();
}
