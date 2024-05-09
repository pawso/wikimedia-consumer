package org.pawel.consumer;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pawel.producer.KafkaMessageProducer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventSourceHandler implements EventHandler {

    final KafkaMessageProducer kafkaProducer;

    @Override
    public void onOpen() {
    }

    @Override
    public void onClosed() {
        kafkaProducer.close();
    }

    @Override
    public void onMessage(String s, MessageEvent messageEvent) {
        log.info("Received Wikimedia event: {}", messageEvent.getData());
        kafkaProducer.consume(null, messageEvent.getData());
    }

    @Override
    public void onComment(String s) {

    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Error when reading stream", throwable);
    }
}
