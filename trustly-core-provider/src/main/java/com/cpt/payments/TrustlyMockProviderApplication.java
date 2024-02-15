package com.cpt.payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication(scanBasePackages = { "com.cpt.payments" })
public class TrustlyMockProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrustlyMockProviderApplication.class, args);
		System.out.println("Application Started");
	}

}
