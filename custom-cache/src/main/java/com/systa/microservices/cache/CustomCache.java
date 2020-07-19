package com.systa.microservices.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomCache {

	@SuppressWarnings("rawtypes")
	private static Map<Object, Cacheable> cache = new ConcurrentHashMap<>();
	
	static{
		Thread cleaner = new Thread(
			() -> {
				
				try{
					while(true){
						log.info("Clearing Cache");
						CustomCache.cache.values().forEach(i -> {
							if(i.isExpired()){
								CustomCache.cache.remove(i.getKey());
							}
						});
						Thread.sleep(50000);
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			
		});
		
		cleaner.setPriority(1);
		cleaner.start();
	}
	
	@SuppressWarnings("rawtypes")
	public static Cacheable put(Cacheable entry){
		return cache.put(entry.getKey(), entry);
	}
	
	@SuppressWarnings("rawtypes")
	public static Cacheable get(Object key){
		Cacheable entry = cache.get(key);
		if(entry == null)
			return null;
		else if(entry.isExpired())
			return null;
		else
			return entry;
	}
}
