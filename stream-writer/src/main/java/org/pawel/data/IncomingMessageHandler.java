package org.pawel.data;

import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pawel.redis.writer.RedisWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class IncomingMessageHandler {

    private final RedisWriter redisWriter;

    public void handleMessage(String message) {
        var jsonObject = JsonParser.parseString(message).getAsJsonObject();

        var key = jsonObject.getAsJsonObject("meta").get("id").getAsString();

        redisWriter.write(key, message);
    }
}
