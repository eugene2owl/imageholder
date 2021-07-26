package com.example.imageholder.imagenotificationsubscription.validator;

import com.example.imageholder.imagenotificationsubscription.repository.ImageNotificationSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageNotificationSubscriptionValidator {

    private final ImageNotificationSubscriptionRepository repository;

    @Autowired
    public ImageNotificationSubscriptionValidator(ImageNotificationSubscriptionRepository repository) {
        this.repository = repository;
    }

    public void validateSubscription(String email, String topicArn) {
        if (isEmailSubscribed(email, topicArn)) {
            throw new RuntimeException("Email " + email + " is already subscribed to the topic with ARN " + topicArn);
        }
    }

    public void validateUnsubscription(String email, String topicArn) {
        if (!isEmailSubscribed(email, topicArn)) {
            throw new RuntimeException("Email " + email + " is not subscribed to the topic with ARN " + topicArn);
        }
    }

    private boolean isEmailSubscribed(String email, String topicArn) {
        return repository.existsByEmailAndTopicArn(email, topicArn);
    }
}
