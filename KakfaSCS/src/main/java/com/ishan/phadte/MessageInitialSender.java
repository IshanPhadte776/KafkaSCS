package com.ishan.phadte;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

// Broker1Producer
@Service
public class MessageInitialSender {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private String sourceTopic;

    public MessageInitialSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void sendMessage(String message) {
        kafkaTemplate.send(sourceTopic, message);
    }
    


}
