package org.pawel.wikimediaconsumer.consumer;

import com.launchdarkly.eventsource.EventSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pawel.wikimediaconsumer.config.EventSourceConfig;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventSourceConsumer implements ApplicationRunner {

    final EventSourceConfig eventSourceConfig;

    final EventSourceHandler eventSourceHandler;

    public void run(ApplicationArguments arg0) {
        String url = eventSourceConfig.getUrl();
        EventSource.Builder builder = new EventSource.Builder(eventSourceHandler, URI.create(url));
        EventSource eventSource = builder.build();
        eventSource.start();

        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.info("EventSourceConsumer thread interrupted");
        }
    }
}
