package org.pawel.wikimediaconsumer.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.pawel.wikimediaconsumer.config.KafkaProducerConfig;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocalKafkaProducer {

    final KafkaProducerConfig kafkaProducerConfig;
    final LazyKafkaProducerProvider producerProvider;

    public void consume(String key, String message) {

        var producer = producerProvider.get();
        ProducerRecord<String, String> record = new ProducerRecord<>(kafkaProducerConfig.getTopic(), message);
        producer.send(record);
    }
}
