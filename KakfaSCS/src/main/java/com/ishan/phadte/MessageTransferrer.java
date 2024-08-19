package com.ishan.phadte;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

//Message Tranferer
public class MessageTransferrer {


    private final KafkaTemplate<String, String> kafkaTemplate;

    // private String sourceTopic;
    private String forwardTopic;

    public MessageTransferrer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "${spring.cloud.stream.bindings.consumer-in-0.destination}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenAndForward(String message) {
        kafkaTemplate.send(forwardTopic, message);
    }

}