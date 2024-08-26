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

import com.ishan.phadte.util.Reservation;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

	public String getCurrentTime() {
        // Get the current time
        LocalTime currentTime = LocalTime.now();
        
        // Format the time to get hours, minutes, and seconds
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return currentTime.format(formatter);
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void placeReservation() {
        reservationID++;

        // Send to the first topic
        streamBridge.send("producer-out-0", 
            MessageBuilder.withPayload(new Reservation(reservationID, "Ishan", 5, getCurrentTime(),false ))
            .setHeader("spring.cloud.stream.sendto.destination", "first-topic")
            .build());    
    }

    public void sendReservationConfirm(int id, String name, int partySize, String placedOrderTime, boolean sentReservationConfirm) {
		// Send a confirmation message as a plain string to the second topic
		streamBridge.send("producer-out-0", 
			MessageBuilder.withPayload(new Reservation(id,name,partySize,placedOrderTime,true))
			.setHeader("spring.cloud.stream.sendto.destination", "second-topic")
			.build());
	}

    @Bean
    public Consumer<Reservation> consumer() {
        return payload -> {


			if (payload.getSentReservationConfirm()) {
				String confirmationMessage = "Reservation for " + payload.getName() + " has been confirmed.";
                System.out.println(confirmationMessage);

			}
            // Check if the payload is a Reservation
            else  {
                Reservation reservation = (Reservation) payload;
                System.out.println("Received Reservation: " + reservation);

                // Perform Reservation-specific logic here
                sendReservationConfirm(payload.getId(), payload.getName(), payload.getPartySize(), payload.getPlacedOrderTime(), true);
            
			}
        };
    }

}

