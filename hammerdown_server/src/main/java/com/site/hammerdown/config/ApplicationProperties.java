package com.site.hammerdown.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "hammerdown")
@Data
public class ApplicationProperties {
    private final String name;
}
