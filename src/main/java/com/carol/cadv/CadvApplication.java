package com.carol.cadv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CadvApplication {

	public static void main(String[] args) {
		SpringApplication.run(CadvApplication.class, args);
	}

}
