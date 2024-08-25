package com.ishan.phadte.dto.coverters;

import java.io.IOException;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishan.phadte.dto.Reservation;
import com.ishan.phadte.dto.Message;

public class ReservationDeSerializer implements Deserializer<Object>{
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object deserialize(String topic, byte[] data) {
        try {
            // Convert byte[] to String
            String jsonData = new String(data);

            // Try deserializing as Reservation
            try {
                return objectMapper.readValue(jsonData, Reservation.class);
            } catch (IOException e) {
                // If deserialization fails, attempt deserializing as Message
                return objectMapper.readValue(jsonData, Message.class);
            }
        } catch (IOException e) {
            throw new SerializationException("Error deserializing JSON to Object", e);
        }
    }
    
}
