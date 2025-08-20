package com.example.springbootthread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringBootThreadApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootThreadApplication.class, args);
	}

}
