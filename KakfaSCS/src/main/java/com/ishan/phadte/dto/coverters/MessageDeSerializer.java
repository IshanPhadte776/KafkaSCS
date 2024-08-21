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
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bais)) {

            // Read header length and header
            int headerLength = ois.read();
            byte[] headerBytes = new byte[headerLength];
            ois.read(headerBytes);
            Headers headers = objectMapper.readValue(headerBytes, Headers.class);

            // Read payload length and payload
            int payloadLength = ois.read();
            byte[] payloadBytes = new byte[payloadLength];
            ois.read(payloadBytes);
            Object payload = objectMapper.readValue(payloadBytes, Object.class);

            return new Message(headers, payload);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error serializing message", e);
        } catch (IOException e) {
            throw new SerializationException("Error combining serialized parts", e);
        }
    }
}

