package com.example.imageholder.imagenotificationsubscription.dto;

import com.example.imageholder.common.Dto;

public class ImageNotificationSubscriptionResultDto implements Dto {

    private String email;

    private String subscriptionArn;

    private String topicArn;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubscriptionArn() {
        return subscriptionArn;
    }

    public void setSubscriptionArn(String subscriptionArn) {
        this.subscriptionArn = subscriptionArn;
    }

    public String getTopicArn() {
        return topicArn;
    }

    public void setTopicArn(String topicArn) {
        this.topicArn = topicArn;
    }
}
