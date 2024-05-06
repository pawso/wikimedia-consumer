package org.pawel.wikimediaconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("org.pawel.wikimediaconsumer.config")
public class WikimediaConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WikimediaConsumerApplication.class, args);
	}

}
