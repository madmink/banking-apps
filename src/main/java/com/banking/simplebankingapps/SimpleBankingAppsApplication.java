package com.banking.simplebankingapps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication //1231
@ComponentScan
public class SimpleBankingAppsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleBankingAppsApplication.class, args);
	}

}
