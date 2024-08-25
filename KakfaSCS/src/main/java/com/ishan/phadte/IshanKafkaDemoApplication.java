package com.ishan.phadte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.ishan.phadte.dto.Message;
import com.ishan.phadte.dto.Reservation;

import java.util.function.Consumer;

@SpringBootApplication
@EnableScheduling
public class IshanKafkaDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(IshanKafkaDemoApplication.class, args);
    }

    @Autowired
    private StreamBridge streamBridge;

    private int reservationID = 0;

    @Bean
    public CommandLineRunner run() {
        return args -> {
            System.out.println("Hello");
        };
    }

    @Scheduled(cron = "*/2 * * * * *")
    public void placeReservation() {
        reservationID++;

        // Send to the first topic
        streamBridge.send("producer-out-0", 
            MessageBuilder.withPayload(new Reservation(reservationID, "Name", 5, "Time"))
            .setHeader("spring.cloud.stream.sendto.destination", "first-topic")
            .build());    
    }

    public void sendReservationConfirm(String name) {
        // Send to the second topic
        streamBridge.send("producer-out-0", 
            MessageBuilder.withPayload(new Message("Sending Confirmation Message"))
            .setHeader("spring.cloud.stream.sendto.destination", "second-topic")
            .build());    
    }

    @Bean
    public Consumer<Object> consumer() {
        return payload -> {
            // Check if the payload is a Reservation
            if (payload instanceof Reservation) {
                Reservation reservation = (Reservation) payload;
                System.out.println("Received Reservation: " + reservation);

                // Perform Reservation-specific logic here
                sendReservationConfirm(reservation.getName());
            } 
            // Check if the payload is a Message
            else if (payload instanceof Message) {
                Message message = (Message) payload;
                System.out.println("Received Message: " + message);
                
                // Perform Message-specific logic here
            } else {
                System.out.println("Received unknown payload type: " + payload);
            }
        };
    }
}
