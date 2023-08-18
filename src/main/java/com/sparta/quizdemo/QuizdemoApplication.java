package com.sparta.quizdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class QuizdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizdemoApplication.class, args);
	}

}
