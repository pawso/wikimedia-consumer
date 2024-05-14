package org.pawel.kafka.reader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.pawel.config.KafkaConsumerConfig;
import org.pawel.data.IncomingMessageHandler;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaStreamConsumer implements ApplicationRunner {

    final KafkaConsumerConfig kafkaConsumerConfig;
    final KafkaStreamConsumerProvider kafkaStreamConsumerProvider;
    final IncomingMessageHandler incomingMessageHandler;

    @Override
    public void run(ApplicationArguments args) {

        var kafkaConsumer = kafkaStreamConsumerProvider.instance();

        while(true) {
            ConsumerRecords<String, String> records =
                    kafkaConsumer.poll(Duration.ofMillis(1000));

            log.info("Fetched records: {}", records.count());
            for (ConsumerRecord<String, String> record : records){
                // log.info("Key: {}, Value: {}", record.key(), record.value());
                // log.info("Partition: {}, Offset:{}", record.partition(), record.offset());
                incomingMessageHandler.handleMessage(record.value());
            }
        }
    }
}
