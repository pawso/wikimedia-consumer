package org.pawel.wikimediaconsumer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "kafka.producer")
@ConfigurationPropertiesScan
@Data
@Validated
public class KafkaProducerConfig {
    String bootstrapServers;
    String saslMechanism;
    String securityProtocol;
    String saslJaasConfig;
    String keySerializer;
    String valueSerializer;
    String topic;
}
