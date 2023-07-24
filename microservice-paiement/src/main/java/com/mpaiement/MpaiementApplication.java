package com.mpaiement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.mpaiement")
@EnableEurekaClient
public class MpaiementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MpaiementApplication.class, args);
	}
}
