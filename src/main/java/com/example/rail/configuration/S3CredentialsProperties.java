package com.example.rail.configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class S3CredentialsProperties {
    private String accessKey;
    private String secretKey;
}
