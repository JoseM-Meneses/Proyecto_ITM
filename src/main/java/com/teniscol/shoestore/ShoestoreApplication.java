package com.teniscol.shoestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ShoestoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoestoreApplication.class, args);
	}

}
