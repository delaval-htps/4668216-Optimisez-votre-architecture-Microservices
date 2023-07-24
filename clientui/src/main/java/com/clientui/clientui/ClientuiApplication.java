package com.clientui.clientui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.clientui")
@EnableEurekaClient
public class ClientuiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientuiApplication.class, args);
	}

}
