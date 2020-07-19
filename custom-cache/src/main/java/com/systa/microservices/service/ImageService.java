package com.systa.microservices.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.systa.microservices.cache.CacheEntry;
import com.systa.microservices.cache.Cacheable;
import com.systa.microservices.cache.CustomCache;

@Service
public class ImageService {
	
	private static final String BANNER_IMAGE_CACHE_KEY = "bannerImages";

	
	/*
	 * Add image to cache, usually we should store it in DB.
	 */
	@SuppressWarnings("unchecked")
	public boolean addImage(String imageUrl){
		
		Cacheable<String, List<String>> cache = CustomCache.get(BANNER_IMAGE_CACHE_KEY);
		if(cache != null){ // images list is available in cache
			List<String> images = cache.getValue();
			images.add(imageUrl);
		}
		else{ // images list is not available in cache, creating a list and adding it to the cache
			List<String> list = new ArrayList<>();
			list.add(imageUrl);
			cache = new CacheEntry<String, List<String>>(BANNER_IMAGE_CACHE_KEY, list,
					TimeUnit.MINUTES, 60); // making cache available for 60 mins
			CustomCache.put(cache);
		}
		return true;
	}
	
	
	/*
	 * Reading image url's from Cache directly, usually we read data from db and cache it 
	 * it data is not in cache then read from Db and store it in cache
	 * first read data from cache, if not available read it from DB and cache it.
	 */
	@SuppressWarnings("unchecked")
	public List<String> getImages(){
		Cacheable<String, List<String>> cache = CustomCache.get(BANNER_IMAGE_CACHE_KEY);
		if(cache != null)
			return cache.getValue();
		
		return new ArrayList<>();
	}
}
