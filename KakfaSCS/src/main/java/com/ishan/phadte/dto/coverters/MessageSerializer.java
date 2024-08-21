package com.ishan.phadte.dto.coverters;

import com.ishan.phadte.dto.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import java.util.Map;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class MessageSerializer implements Serializer<Message> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, Message data) {
        try {
            // Check if the payload is already a byte[]
            if (data.getPayload() instanceof byte[]) {
                System.out.println("Sending a byte[] message");
                return (byte[]) data.getPayload();
            }

            // System.out.println("Sending a string message");
            // System.out.println(data);

            // System.out.println(objectMapper.writeValueAsBytes(data));


            // // Otherwise, serialize the entire Message object
            // return objectMapper.writeValueAsBytes(data);

            // Serialize headers and payload separately
            byte[] headerBytes = objectMapper.writeValueAsBytes(data.getHeaders());
            byte[] payloadBytes = objectMapper.writeValueAsBytes(data.getPayload());

            // Combine headerBytes and payloadBytes into a single byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(headerBytes.length); // Write header length
            outputStream.write(headerBytes); // Write header
            outputStream.write(payloadBytes.length); // Write payload length
            outputStream.write(payloadBytes); // Write payload

            return outputStream.toByteArray();
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error serializing message", e);
        } catch (IOException e) {
            throw new SerializationException("Error combining serialized parts", e);
        }
    }
}