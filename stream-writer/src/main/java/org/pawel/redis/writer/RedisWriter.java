package org.pawel.redis.writer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RedisWriter {

    private final RedisConnectionProvider redisConnectionProvider;

    public void write(String key, String value) {
        redisConnectionProvider.instance().set(key, value);
    }
}
