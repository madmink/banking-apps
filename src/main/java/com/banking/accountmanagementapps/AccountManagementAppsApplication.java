package com.banking.accountmanagementapps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication //1231
@ComponentScan
public class AccountManagementAppsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountManagementAppsApplication.class, args);
	}

}
