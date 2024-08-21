package com.ishan.phadte.dto;

import java.util.Map;

import com.ishan.phadte.dto.Headers;

public record Reservation (String id, String name, Map<String, String> headers){
    // public String getName(){
    //     return name;
    // }

    // public String getPartySize(){
    //     return partySize;
    // }
}
