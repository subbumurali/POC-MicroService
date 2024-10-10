package com.example.LICPOC.POC_MicroService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
//@EnableMongoRepositories
public class PocMicroServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(PocMicroServiceApplication.class, args);

		System.out.println("********************************************************************************");
			System.out.println("Application started successfully !!!");
		System.out.println("********************************************************************************");
	}
}

