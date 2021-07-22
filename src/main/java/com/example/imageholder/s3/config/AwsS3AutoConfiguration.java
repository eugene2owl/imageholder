package com.example.imageholder.s3.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.example.imageholder.s3.properties.AwsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync(proxyTargetClass = true)
@ComponentScan(basePackageClasses = AwsS3AutoConfiguration.class)
@ConditionalOnProperty(value = "aws.s3.enabled", havingValue = "true")
public class AwsS3AutoConfiguration {

    @Autowired
    private AwsProperties awsProperties;

    @Primary
    @Bean
    public AmazonS3 awsS3Client() {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsProperties.buildCredentials()))
                .withRegion(Regions.fromName(awsProperties.getRegion()))
                .build();
    }
}
