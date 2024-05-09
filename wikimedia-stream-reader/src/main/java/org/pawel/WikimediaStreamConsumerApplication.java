package org.pawel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("org.pawel.config")
public class WikimediaStreamConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WikimediaStreamConsumerApplication.class, args);
	}

}
