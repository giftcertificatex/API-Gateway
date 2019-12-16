package com.giftok.gateway.topic;

import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectSubscriptionName;

public class CertificateCreatedSubscriber {
    private static final String PROJECT = "single-outrider-260808";
    private static final String SUBSCRIPTION = "certificate-created-subscription";

    private final static MessageReceiver receiver = (message, consumer) -> {
        String messageId = message.getMessageId();
        ByteString messageData = message.getData();
        System.out.println("Message Id: " + messageId);
        try {
            String certMessage = CertificateProto.Certificate.parseFrom(messageData).toString();
            System.out.println(certMessage);

            // Ack only after all work for the message is complete.
            consumer.ack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public void await() {
        ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(PROJECT, SUBSCRIPTION);
        try {
            // create a subscriber bound to the asynchronous message receiver
            Subscriber subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
            subscriber.startAsync().awaitRunning();
            // Allow the subscriber to run indefinitely unless an unrecoverable error occurs.
            subscriber.awaitTerminated();
        } catch (IllegalStateException e) {
            System.out.println("Subscriber unexpectedly stopped: " + e);
        }
    }
}