package com.example.imageholder.aws.lamdba.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
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
@ComponentScan(basePackageClasses = AwsLambdaAutoConfiguration.class)
@ConditionalOnProperty(value = "aws.lambda.enabled", havingValue = "true")
public class AwsLambdaAutoConfiguration {

    private final AwsProperties awsProperties;

    @Autowired
    public AwsLambdaAutoConfiguration(AwsProperties awsProperties) {
        this.awsProperties = awsProperties;
    }

    @Primary
    @Bean
    public AWSLambda awsLambdaClient() {
        return AWSLambdaClientBuilder
                .standard()
                .withRegion(Regions.fromName(awsProperties.getRegion()))
                .build();
    }
}
