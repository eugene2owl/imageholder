package com.example.imageholder.aws.sns.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
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
@ComponentScan(basePackageClasses = AwsSnsAutoConfiguration.class)
@ConditionalOnProperty(value = "aws.sns.enabled", havingValue = "true")
public class AwsSnsAutoConfiguration {

    private final AwsProperties awsProperties;

    @Autowired
    public AwsSnsAutoConfiguration(AwsProperties awsProperties) {
        this.awsProperties = awsProperties;
    }

    @Primary
    @Bean
    public AmazonSNS awsSNSClient() {
        return AmazonSNSClientBuilder
                .standard()
                .withRegion(Regions.fromName(awsProperties.getRegion()))
                .build();
    }
}
