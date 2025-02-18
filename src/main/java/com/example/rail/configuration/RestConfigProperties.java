package com.example.rail.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "rest.currency-service")
public class RestConfigProperties {
    private String host;
    private Map<String, String> methods;

}
