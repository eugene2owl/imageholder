package com.example.imageholder.aws.sns.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.UnsubscribeRequest;
import com.example.imageholder.aws.sns.AwsSNSService;
import com.example.imageholder.imagenotificationsubscription.dto.ImageNotificationSubscriptionResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AwsSNSServiceImpl implements AwsSNSService {

    private static final String EMAIL_SUBSCRIPTION_PROTOCOL = "email";

    private final AmazonSNS amazonSNSClient;

    @Autowired
    public AwsSNSServiceImpl(AmazonSNS amazonSNSClient) {
        this.amazonSNSClient = amazonSNSClient;
    }

    @Override
    public ImageNotificationSubscriptionResultDto subscribeEmail(String email, String topicArn) {
        try {
            var request = buildSubscribeRequest(email, topicArn);
            var result = amazonSNSClient.subscribe(request);

            log.info("Subscription status was " + result.getSdkHttpMetadata().getHttpStatusCode());
            log.info("Subscription ARN: " + result.getSubscriptionArn());
            return buildSubscribeResult(email, request.getTopicArn(), result.getSubscriptionArn());
        } catch (AmazonClientException amazonClientException) {
            log.error("Error subscribing email to the SNS topic ", amazonClientException);
            throw amazonClientException;
        }
    }

    @Override
    public void unsubscribeByArn(String subscriptionArn) {
        try {
            var request = new UnsubscribeRequest().withSubscriptionArn(subscriptionArn);
            var result = amazonSNSClient.unsubscribe(request);

            log.info("Unsubscription status was " + result.getSdkHttpMetadata().getHttpStatusCode());
            log.info("Subscription ARN: " + request.getSubscriptionArn());
        } catch (AmazonClientException amazonClientException) {
            log.error("Error unsubscribing email of the SNS topic ", amazonClientException);
            throw amazonClientException;
        }
    }

    @Override
    public void publishMessage(String message, String topicArn) {
        try {
            PublishRequest request = new PublishRequest()
                    .withMessage(message)
                    .withTopicArn(topicArn);
            PublishResult result = amazonSNSClient.publish(request);

            log.info("Message sending status was " + result.getSdkHttpMetadata().getHttpStatusCode());
            log.info("Message id: " + result.getMessageId());
        } catch (AmazonClientException amazonClientException) {
            log.error("Error sending message to the SNS topic ", amazonClientException);
            throw amazonClientException;
        }
    }

    private SubscribeRequest buildSubscribeRequest(String email, String topicArn) {
        return new SubscribeRequest()
                .withProtocol(EMAIL_SUBSCRIPTION_PROTOCOL)
                .withEndpoint(email)
                .withReturnSubscriptionArn(true)
                .withTopicArn(topicArn);
    }

    private ImageNotificationSubscriptionResultDto buildSubscribeResult(
            String email,
            String topicArn,
            String subscriptionArn
    ) {
        var result = new ImageNotificationSubscriptionResultDto();
        result.setEmail(email);
        result.setTopicArn(topicArn);
        result.setSubscriptionArn(subscriptionArn);
        return result;
    }
}
