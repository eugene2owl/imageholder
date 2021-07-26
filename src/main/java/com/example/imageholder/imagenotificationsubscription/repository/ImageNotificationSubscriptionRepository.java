package com.example.imageholder.imagenotificationsubscription.repository;

import com.example.imageholder.imagenotificationsubscription.model.ImageNotificationSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageNotificationSubscriptionRepository extends JpaRepository<ImageNotificationSubscription, Long> {

    @Query(
            "SELECT ins.subscriptionArn FROM ImageNotificationSubscription AS ins " +
                    "WHERE ins.email = :email AND ins.topicArn = :topicArn"
    )
    Optional<String> findSubscriptionArnByEmailAndTopicArn(
            @Param("email") String email,
            @Param("topicArn") String topicArn
    );

    void deleteAllBySubscriptionArn(String subscriptionArn);

    boolean existsByEmailAndTopicArn(String email, String topicArn);
}
