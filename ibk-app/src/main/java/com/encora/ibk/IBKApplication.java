package com.encora.ibk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class IBKApplication {

	public static void main(String[] args) {
		SpringApplication.run(IBKApplication.class, args);
	}

}
