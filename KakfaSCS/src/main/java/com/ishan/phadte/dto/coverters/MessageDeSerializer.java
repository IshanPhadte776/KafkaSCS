package com.ishan.phadte.dto.coverters;

import com.ishan.phadte.dto.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class MessageDeSerializer implements Deserializer<Message> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Message deserialize(String topic, byte[] data) {
        try {
            // Attempt to deserialize the data into a Message object
            Message message = objectMapper.readValue(data, Message.class);
            
            // Check if the payload is a String and convert it to byte[] if necessary
            if (message.getPayload() instanceof String) {
                message = new Message(message.header(), ((String) message.getPayload()).getBytes());
            }

            return message;
        } catch (IOException e) {
            throw new SerializationException("Error deserializing message", e);
        }
    }
}
