package com.example.BinasJc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.BinasJc", "controller", "model", "repository"})
public class BinasJcApplication {

	public static void main(String[] args) {
		SpringApplication.run(BinasJcApplication.class, args);
	}
}
