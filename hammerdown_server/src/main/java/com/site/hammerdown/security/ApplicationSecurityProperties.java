package com.site.hammerdown.security;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "app.security")
@Data
public class ApplicationSecurityProperties {

    private Integer jwtExpirationMs;
    private String jwtSecret;
    private String jwtCookieName;
    private String frontEndUrl;
}
