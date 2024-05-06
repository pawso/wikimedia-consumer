package org.pawel.wikimediaconsumer.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.ConcurrentInitializer;
import org.apache.commons.lang3.concurrent.LazyInitializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.pawel.wikimediaconsumer.config.KafkaProducerConfig;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocalKafkaProducer {

    final KafkaProducerConfig kafkaProducerConfig;

    public void consume(String key, String message) {
        KafkaProducer producer;
        try {
            producer = createOrGetProducer();
        } catch (ConcurrentException e) {
            log.error("Could not create kafka producer", e);
            throw new RuntimeException(e);
        }

        ProducerRecord<String, String> record = new ProducerRecord<>(kafkaProducerConfig.getTopic(), message);

        producer.send(record);
    }

    private KafkaProducer<String, String> createOrGetProducer() throws ConcurrentException {
        var props = createProperties();

        ConcurrentInitializer<KafkaProducer<String, String>> lazyInitializer = new LazyInitializer<>() {
            @Override
            protected KafkaProducer<String, String> initialize()  {
                return new KafkaProducer<>(props);
            }
        };

        return lazyInitializer.get();
    }

    private Properties createProperties() {
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
