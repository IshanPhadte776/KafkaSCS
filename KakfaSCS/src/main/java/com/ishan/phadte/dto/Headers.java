package com.ishan.phadte.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonDeserialize(as = Headers.class)
public class Headers {
    private final Map<String, Object> headers;

    public Headers() {
        this.headers = new HashMap<>();
    }

    @JsonCreator
    public Headers(@JsonProperty("headers") Map<String, Object> headers) {
        this.headers = headers != null ? new HashMap<>(headers) : new HashMap<>();
    }

    public void put(String key, Object value) {
        headers.put(key, value);
    }

    public Object get(String key) {
        return headers.get(key);
    }

    public Map<String, Object> getAll() {
        return new HashMap<>(headers);
    }

    @Override
    public String toString() {
        return "Headers{" +
               "headers=" + headers +
               '}';
    }
   
}
