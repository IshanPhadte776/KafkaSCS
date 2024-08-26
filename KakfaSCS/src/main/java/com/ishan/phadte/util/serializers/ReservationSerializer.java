package com.ishan.phadte.util.serializers;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishan.phadte.util.Reservation;

public class ReservationSerializer implements Serializer<Reservation>{
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, Reservation reservation) {
        try {
            return objectMapper.writeValueAsBytes(reservation);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e);
        }

    }
    
}
