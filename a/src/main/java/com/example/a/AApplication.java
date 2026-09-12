package com.example.a;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "Payment Service API",
		version = "v1",
		description = "Accepts payments and publishes payment events to Kafka."))
public class AApplication {

	public static void main(String[] args) {
		SpringApplication.run(AApplication.class, args);
	}

}
