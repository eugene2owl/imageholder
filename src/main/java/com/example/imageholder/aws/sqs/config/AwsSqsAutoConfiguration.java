package com.example.imageholder.aws.sqs.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.example.imageholder.aws.common.AwsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync(proxyTargetClass = true)
@ComponentScan(basePackageClasses = AwsSqsAutoConfiguration.class)
@ConditionalOnProperty(value = "aws.sqs.enabled", havingValue = "true")
public class AwsSqsAutoConfiguration {

    private final AwsProperties awsProperties;

    @Autowired
    public AwsSqsAutoConfiguration(AwsProperties awsProperties) {
        this.awsProperties = awsProperties;
    }

    @Primary
    @Bean
    public AmazonSQS awsSQSClient() {
        return AmazonSQSClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsProperties.buildCredentials()))
                .withRegion(Regions.fromName(awsProperties.getRegion()))
                .build();
    }
}
