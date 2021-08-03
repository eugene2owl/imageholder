package com.example.imageholder.imagenotificationsubscription.service.impl;

import com.example.imageholder.aws.sns.AwsSNSService;
import com.example.imageholder.aws.sns.AwsSNSTopicArnProvider;
import com.example.imageholder.imagenotificationsubscription.dto.transformer.ImageNotificationSubscriptionResultTransformer;
import com.example.imageholder.imagenotificationsubscription.repository.ImageNotificationSubscriptionRepository;
import com.example.imageholder.imagenotificationsubscription.service.ImageNotificationSubscriptionService;
import com.example.imageholder.imagenotificationsubscription.validator.ImageNotificationSubscriptionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ImageNotificationSubscriptionServiceImpl implements ImageNotificationSubscriptionService {

    private final ImageNotificationSubscriptionRepository repository;
    private final AwsSNSService awsSNSService;
    private final ImageNotificationSubscriptionResultTransformer transformer;
    private final AwsSNSTopicArnProvider awsSNSTopicArnProvider;
    private final ImageNotificationSubscriptionValidator validator;

    @Autowired
    public ImageNotificationSubscriptionServiceImpl(
            ImageNotificationSubscriptionRepository repository,
            AwsSNSService awsSNSService,
            ImageNotificationSubscriptionResultTransformer transformer,
            AwsSNSTopicArnProvider awsSNSTopicArnProvider,
            ImageNotificationSubscriptionValidator validator
    ) {
        this.repository = repository;
        this.awsSNSService = awsSNSService;
        this.transformer = transformer;
        this.awsSNSTopicArnProvider = awsSNSTopicArnProvider;
        this.validator = validator;
    }

    @Override
    public void subscribeEmail(String email) {
        var topicArn = awsSNSTopicArnProvider.provideSNSTopicArn();
        validator.validateSubscription(email, topicArn);

        var result = awsSNSService.subscribeEmail(email, topicArn);
        var entity = transformer.transform(result);
        repository.save(entity);
    }

    @Override
    @Transactional
    public void unsubscribeEmail(String email) {
        var topicArn = awsSNSTopicArnProvider.provideSNSTopicArn();
        validator.validateUnsubscription(email, topicArn);

        var subscriptionArn = getSubscriptionArnByEmailAndTopicArn(email, topicArn);
        awsSNSService.unsubscribeByArn(subscriptionArn);
        repository.deleteAllBySubscriptionArn(subscriptionArn);
    }

    private String getSubscriptionArnByEmailAndTopicArn(String email, String topicArn) {
        return repository.findSubscriptionArnByEmailAndTopicArn(email, topicArn)
                .orElseThrow(() -> new RuntimeException("No notification subscription found for email " + email));
    }
}
