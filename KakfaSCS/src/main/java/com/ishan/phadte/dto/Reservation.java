package com.ishan.phadte.dto;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record Reservation (int id, String name, int partySize, String placedOrderTime){

    public String getName(){
        return name;
    }

    public int getId(){
        return id;
    }

    public int getPartySize(){
        return partySize;
    }

    public String getPlacedOrderTime(){
        return placedOrderTime;
    }

    @Override
    public String toString() {
        return String.format("Reservation [ID=%d, Name='%s', Party Size=%d, Placed Order Time='%s']",
                id, name, partySize, placedOrderTime);
    }
    

}
