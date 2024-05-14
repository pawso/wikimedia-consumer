package org.pawel.producer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.jetbrains.annotations.NotNull;
import org.pawel.config.KafkaProducerConfig;
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
        if (producer == null) {
            init();
        }

        return producer;
    }

    void deinit() {
        producer.flush();
        log.info("Closing provider");
        producer.close();
        producer = null;
    }

    @NotNull
    private Properties extractProperties() {
        Properties properties = new Properties();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProducerConfig.getBootstrapServers());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProducerConfig.getKeySerializer());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProducerConfig.getValueSerializer());

        properties.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProducerConfig.getLingerMs());
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProducerConfig.getBatchSize());
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, kafkaProducerConfig.getCompressionType());

        properties.put("sasl.mechanism", kafkaProducerConfig.getSaslMechanism());
        properties.put("security.protocol", kafkaProducerConfig.getSecurityProtocol());
        properties.put("sasl.jaas.config", kafkaProducerConfig.getSaslJaasConfig());

        return properties;
    }
}
