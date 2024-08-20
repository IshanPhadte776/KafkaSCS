package com.ishan.phadte.dto;

// public record Message(String name) {
// }

public record Message (String header, Object payload){
    public Object getPayload(){
        return payload;
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
            "header='" + header + '\'' +
            ", payload=" + payloadStr +
            '}';
    }
}
