package com.example.imageholder.aws.sns.impl;

import com.example.imageholder.aws.sns.AwsSNSTopicArnProvider;
import com.example.imageholder.aws.sns.properties.AwsSNSProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AwsPropertySNSTopicArnProvider implements AwsSNSTopicArnProvider {

    private final AwsSNSProperties properties;

    @Autowired
    public AwsPropertySNSTopicArnProvider(AwsSNSProperties properties) {
        this.properties = properties;
    }

    @Override
    public String provideSNSTopicArn() {
        return properties.getTopicArn();
    }
}
