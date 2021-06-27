package org.smartframework.cloud.examples.basic.auth;

import org.smartframework.cloud.examples.framework.annotation.SmartApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SmartApplication
@EnableTransactionManagement
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}