package com.example.imageholder.aws.s3.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
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

    @Primary
    @Bean
    public AmazonS3 awsS3Client(@Value("${aws.region}") String region) {
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.fromName(region))
                .build();
    }
}
