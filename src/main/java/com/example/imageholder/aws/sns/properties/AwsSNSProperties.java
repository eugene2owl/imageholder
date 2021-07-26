package com.example.imageholder.aws.sns.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("aws.sns")
public class AwsSNSProperties {

    private String topicArn;

    public String getTopicArn() {
        return topicArn;
    }

    public void setTopicArn(final String topicArn) {
        this.topicArn = topicArn;
    }
}
