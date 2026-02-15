package dev.manoj.servicediscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
//Service discovery server.
public class ServicediscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicediscoveryApplication.class, args);
	}

}
