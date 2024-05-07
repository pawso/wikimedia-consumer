package org.pawel.wikimediaconsumer.producer;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.jetbrains.annotations.NotNull;
import org.pawel.wikimediaconsumer.config.KafkaProducerConfig;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerProvider {
    final KafkaProducerConfig kafkaProducerConfig;

    KafkaProducer<String, String> producer = null;

    @PostConstruct
    private void init() {
        var properties = extractProperties();
        producer = new KafkaProducer<>(properties);
    }

    KafkaProducer<String, String> get() {
        return producer;
    }

    @NotNull
    private Properties extractProperties() {
        Properties properties = new Properties();

        properties.put("bootstrap.servers", kafkaProducerConfig.getBootstrapServers());
        properties.put("sasl.mechanism", kafkaProducerConfig.getSaslMechanism());
        properties.put("security.protocol", kafkaProducerConfig.getSecurityProtocol());
        properties.put("sasl.jaas.config", kafkaProducerConfig.getSaslJaasConfig());
        properties.put("key.serializer", kafkaProducerConfig.getKeySerializer());
        properties.put("value.serializer", kafkaProducerConfig.getValueSerializer());

        return properties;
    }
}
