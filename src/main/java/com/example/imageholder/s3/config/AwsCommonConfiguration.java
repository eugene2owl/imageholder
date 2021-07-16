package com.example.imageholder.s3.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = AwsCommonConfiguration.class)
public class AwsCommonConfiguration {
}
