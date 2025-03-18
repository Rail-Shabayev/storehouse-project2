package com.example.rail.configuration.serviceProperties;

import lombok.Data;

import java.util.Map;

@Data
public class CrmServiceProperties {
    private String host;
    private Map<String, String> methods;
}
