package com.example.imageholder.imagenotificationsubscription.dto.transformer;

import com.example.imageholder.imagenotificationsubscription.dto.ImageNotificationSubscriptionResultDto;
import com.example.imageholder.imagenotificationsubscription.model.ImageNotificationSubscription;
import org.springframework.stereotype.Component;

@Component
public class ImageNotificationSubscriptionResultTransformer {

    public ImageNotificationSubscription transform(ImageNotificationSubscriptionResultDto source) {
        var result = new ImageNotificationSubscription();
        result.setEmail(source.getEmail());
        result.setTopicArn(source.getTopicArn());
        result.setSubscriptionArn(source.getSubscriptionArn());
        return result;
    }
}
