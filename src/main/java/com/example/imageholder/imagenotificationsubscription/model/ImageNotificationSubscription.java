package com.example.imageholder.imagenotificationsubscription.model;

import com.example.imageholder.model.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static com.example.imageholder.model.BaseEntity.ALLOCATION_SIZE;

@Entity
@Table(name = "IMAGE_NOTIFICATION_SUBSCRIPTIONS")
@SequenceGenerator(
        name = "sequence",
        sequenceName = "SEQ_IMAGE_NOTIFICATION_SUBSCRIPTIONS",
        allocationSize = ALLOCATION_SIZE
)
public class ImageNotificationSubscription extends BaseEntity {

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
