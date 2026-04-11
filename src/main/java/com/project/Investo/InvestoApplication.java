package com.project.Investo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InvestoApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvestoApplication.class, args);
	}

}
