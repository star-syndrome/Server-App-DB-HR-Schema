package org.metrodataacademy.TugasSpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class TugasSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(TugasSpringBootApplication.class, args);
		
		log.info("First Project Spring Boot!");
 	}
}