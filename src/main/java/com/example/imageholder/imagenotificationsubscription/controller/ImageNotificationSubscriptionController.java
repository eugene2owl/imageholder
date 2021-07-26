package com.example.imageholder.imagenotificationsubscription.controller;

import com.example.imageholder.imagenotificationsubscription.service.ImageNotificationSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/image-notification-subscriptions", produces = APPLICATION_JSON_VALUE)
public class ImageNotificationSubscriptionController {

    private ImageNotificationSubscriptionService service;

    @Autowired
    public ImageNotificationSubscriptionController(ImageNotificationSubscriptionService service) {
        this.service = service;
    }

    /**
     * Create the notification subscription for provided email.
     * Subscription metadata is stored in the database.
     * Subscription itself is stored in the AWS SNS topic.
     *
     * @param email Email to be subscribed on the topic.
     */
    @PostMapping
    public void create(@RequestBody String email) {
        service.subscribeEmail(email);
    }

    /**
     * Delete the notification subscription for provided email.
     * Subscription metadata is deleted from the database.
     * Subscription itself is deleted from the AWS SNS topic.
     *
     * @param email Email to be unsubscribed from the topic.
     */
    @DeleteMapping
    public void delete(@RequestBody String email) {
        service.unsubscribeEmail(email);
    }
}
