package org.pawel.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "kafka.consumer")
@ConfigurationPropertiesScan
@Data
@Validated
public class KafkaConsumerConfig {
    String bootstrapServers;
    String deserializer;
    String topic;
    String groupId;
    String earliest;
}
