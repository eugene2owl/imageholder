package com.example.imageholder.aws.sqs.impl;

import com.example.imageholder.aws.sqs.AwsSQSQueueNameProvider;
import com.example.imageholder.aws.sqs.properties.AwsSQSProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AwsPropertySQSQueueNameProvider implements AwsSQSQueueNameProvider {

    private final AwsSQSProperties properties;

    @Autowired
    public AwsPropertySQSQueueNameProvider(AwsSQSProperties properties) {
        this.properties = properties;
    }

    @Override
    public String provideSQSQueueName() {
        return properties.getQueue();
    }
}
