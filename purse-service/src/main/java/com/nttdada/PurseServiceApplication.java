package com.nttdada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PurseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PurseServiceApplication.class, args);
	}

}
