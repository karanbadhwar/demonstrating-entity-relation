package com.social.media;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MediaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediaApplication.class, args);
	}


}

//🚀 What is CommandLineRunner?
//CommandLineRunner is a special interface in Spring Boot used to run code after the application context is loaded,
// and just before the Spring Boot app fully starts.
//It’s mostly used for:
//    Seeding the database with initial data
//    Running test logic
//    Running setup code at startup

