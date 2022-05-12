package com.promenade.promenadeapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PromenadeAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PromenadeAppApplication.class, args);
	}

}
