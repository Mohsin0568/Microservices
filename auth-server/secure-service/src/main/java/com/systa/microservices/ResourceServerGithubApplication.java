package com.systa.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// Resource server will only be authenticated by token given by github in another project.
public class ResourceServerGithubApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceServerGithubApplication.class, args);
	}

}
