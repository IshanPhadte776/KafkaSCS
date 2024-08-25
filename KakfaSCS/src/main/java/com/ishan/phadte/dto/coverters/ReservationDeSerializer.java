package com.ishan.phadte.dto.coverters;

import java.io.IOException;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishan.phadte.dto.Reservation;

public class ReservationDeSerializer implements Deserializer<Object>{
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Reservation deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(new String(data), Reservation.class);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }
    
}
