package com.nttdada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TypemonedaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TypemonedaServiceApplication.class, args);
	}

}
