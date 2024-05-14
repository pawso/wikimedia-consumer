package org.pawel.consumer;

import com.launchdarkly.eventsource.EventSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.pawel.config.EventSourceConfig;
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

    public void run(ApplicationArguments arg0) throws Exception {
        String url = eventSourceConfig.getUrl();
        EventSource.Builder builder = new EventSource.Builder(eventSourceHandler, URI.create(url));
        EventSource eventSource = builder.build();

        try {
            eventSource.start();
            setupShutdownHook();
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            log.info("EventSourceConsumer thread interrupted");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            eventSource.close();
            eventSourceHandler.close();
            System.out.println("EventSourceConsumer: closed");
        }
    }

    private void setupShutdownHook() {
        final Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook( new Thread() { public void run() {
            log.info("Shutdown!");
            mainThread.interrupt();
            try {
                mainThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        });
    }
}
