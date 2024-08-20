package com.ishan.phadte.dto.coverters;

import com.ishan.phadte.dto.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import java.util.Map;


public class MessageSerializer implements Serializer<Message> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, Message data) {
        try {
            // Check if the payload is already a byte[]
            if (data.getPayload() instanceof byte[]) {
                return (byte[]) data.getPayload();
            }

            // Otherwise, serialize the entire Message object
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error serializing message", e);
        }
    }
}