package com.ishan.phadte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.ishan.phadte.dto.Message;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SpringBootApplication
@EnableScheduling
public class IshanKafkaDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(IshanKafkaDemoApplication.class, args);
    }

	@Autowired
    private StreamBridge streamBridge;


    @Bean
    public CommandLineRunner run() {
        return args -> {
            System.out.println("Hello");
			sendReservation();
        };
    }
	@Scheduled(cron = "*/2 * * * * *")
	//This is getting called
	public void sendReservation(){
		System.out.println("Pre MEssage");
        streamBridge.send("producer-out-0",new Message(" Reservation"));
		System.out.println("Post MEssage");

    }
	// @Scheduled(cron = "*/2 * * * * *")
	// public void sendReservationConfirm(){
    //     streamBridge.send("reservationConfirmationProducer-out-0",new Message(" Reservation Confirm"));
    // }

	@Bean
    public Consumer<Message> consumer() {
        return message -> System.out.println("Received: " + message);
    }

	@Bean
	//Called 
	public Supplier<Message> producer() {
		return () -> {
			Message message = new Message("jack from Streams");
			System.out.println("Produced: " + message);
			return message;
		};
	}
	
}
