package com.ishan.phadte.dto;

// public record Message(String name) {
// }

public record Message (Headers headers, Object payload){
    public Object getPayload(){
        return payload;
    }

    public Headers getHeaders(){
        return headers;
    }

    @Override
    public String toString() {
        String payloadStr;
        if (payload instanceof byte[]) {
            payloadStr = new String((byte[]) payload);
        } else {
            payloadStr = payload.toString();
        }

        return "Message {" +
            "header='" + headers + '\'' +
            ", payload=" + payloadStr +
            '}';
    }
}
