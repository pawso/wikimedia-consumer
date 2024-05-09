package org.pawel.redis.writer;

import lombok.RequiredArgsConstructor;

import org.pawel.config.RedisConnectionConfig;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class RedisConnectionProvider {

    private final RedisConnectionConfig redisConnectionConfig;
    private Jedis jedis;

    @PostConstruct
    void init() {
        jedis = new Jedis(redisConnectionConfig.getUrl(), Integer.parseInt(redisConnectionConfig.getPort()), true);
        jedis.auth(redisConnectionConfig.getSecret());
    }

    Jedis instance() {
        return jedis;
    }
}
