package org.pawel.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.pawel.config.KafkaProducerConfig;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageProducer {

    final KafkaProducerConfig kafkaProducerConfig;
    final KafkaProducerProvider producerProvider;

    public void consume(String key, String message) {
        var producer = producerProvider.get();

        ProducerRecord<String, String> record = new ProducerRecord<>(kafkaProducerConfig.getTopic(), message);

        producer.send(record);
    }

    public void close() {
        producerProvider.deinit();
    }
}
