package org.pawel.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "redis")
@ConfigurationPropertiesScan
@Data
@Validated
public class RedisConnectionConfig {
    String url;
    String port;
    String secret;
}
