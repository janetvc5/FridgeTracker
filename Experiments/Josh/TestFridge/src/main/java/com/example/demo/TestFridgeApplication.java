package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * To test, start a local database with login options, then change the login settings
 * in the application.properties in src/main/resources. Then, run start.sql in
 * src/main/resources/sql. Run pom.xml as a Maven Build, then run FridgeController
 * as a Springboot app.
 * 
 * To connect, goto localhost:8080/fridges, and
 * localhost:8080/fridges/{id} to see what's in the fridge with that id
 */

@SpringBootApplication
public class TestFridgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestFridgeApplication.class, args);
	}

}

