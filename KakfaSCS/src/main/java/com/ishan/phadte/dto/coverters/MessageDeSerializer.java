package com.ishan.phadte.dto.coverters;

import com.ishan.phadte.dto.Headers;
import com.ishan.phadte.dto.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class MessageDeSerializer implements Deserializer<Message> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Message deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(new String(data), Message.class);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }
}