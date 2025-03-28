package com.example.rail.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class S3ClientConfig {
    private final S3ConfigProperties amazonS3Properties;

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(
                                        amazonS3Properties.getCredentials().getAccessKey(),
                                        amazonS3Properties.getCredentials().getSecretKey())
                        )
                )
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                amazonS3Properties.getEndpoint(),
                                amazonS3Properties.getRegion()
                        )
                )
                .withPathStyleAccessEnabled(true)
                .build();
    }
}

