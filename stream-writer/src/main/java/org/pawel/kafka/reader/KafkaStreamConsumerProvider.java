package org.pawel.kafka.reader;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.pawel.config.KafkaConsumerConfig;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.Collections;
import java.util.Properties;

@RequiredArgsConstructor
@Service
public class KafkaStreamConsumerProvider {

    final KafkaConsumerConfig kafkaConsumerConfig;

    KafkaConsumer<String, String> kafkaConsumer = null;

    KafkaConsumer<String, String> instance() {
        if (kafkaConsumer == null) {
            initializeKafkaConsumer();
        }

        return kafkaConsumer;
    }

    void initializeKafkaConsumer() {
        var properties = createProperties();

        kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(Collections.singletonList(kafkaConsumerConfig.getTopic()));
    }

    private Properties createProperties() {
        var properties = new Properties();

        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConsumerConfig.getBootstrapServers());
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaConsumerConfig.getDeserializer());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaConsumerConfig.getDeserializer());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerConfig.getGroupId());
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConsumerConfig.getAutoOffsetReset());

        properties.put("sasl.mechanism", kafkaConsumerConfig.getSaslMechanism());
        properties.put("security.protocol", kafkaConsumerConfig.getSecurityProtocol());
        properties.put("sasl.jaas.config", kafkaConsumerConfig.getSaslJaasConfig());

        return properties;
    }

    @PreDestroy
    void shutdown() {
        kafkaConsumer.close();
    }
}
