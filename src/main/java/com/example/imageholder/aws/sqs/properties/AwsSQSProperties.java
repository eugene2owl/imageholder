package com.example.imageholder.aws.sqs.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("aws.sqs")
public class AwsSQSProperties {

    private String queue;

    public String getQueue() {
        return queue;
    }

    public void setQueue(final String queue) {
        this.queue = queue;
    }
}
