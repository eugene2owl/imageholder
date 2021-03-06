package com.example.imageholder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EntityScan("com.example.imageholder.**.model")
public class ImageholderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageholderApplication.class, args);
	}
}
