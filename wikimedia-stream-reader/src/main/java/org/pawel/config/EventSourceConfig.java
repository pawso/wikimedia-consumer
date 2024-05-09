package org.pawel.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "eventsource")
@ConfigurationPropertiesScan
@Data
@Validated
public class EventSourceConfig {
    @NotBlank
    private String url;
}
