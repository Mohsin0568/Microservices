package com.systa.microservices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.systa.microservices.service.ImageService;

@RestController
@RequestMapping("/images")
public class ImageController {

	@Autowired
	ImageService imageService;
	
	@GetMapping
	public ResponseEntity<List<String>> getAllImages(){
		return new ResponseEntity<List<String>>(imageService.getImages(), HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping
	public ResponseEntity addImage(@RequestBody String imageUrl){
		imageService.addImage(imageUrl);
		return new ResponseEntity(HttpStatus.CREATED);
	}
}
