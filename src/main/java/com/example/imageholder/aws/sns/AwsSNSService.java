package com.example.imageholder.aws.sns;

import com.example.imageholder.imagenotificationsubscription.dto.ImageNotificationSubscriptionResultDto;

public interface AwsSNSService {

    ImageNotificationSubscriptionResultDto subscribeEmail(String email, String topicArn);

    void unsubscribeByArn(String subscriptionArn);
}
