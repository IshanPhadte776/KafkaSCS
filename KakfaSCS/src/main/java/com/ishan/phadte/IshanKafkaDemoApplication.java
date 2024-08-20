package com.ishan.phadte;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.ishan.phadte.dto.Message;
import org.springframework.messaging.support.MessageBuilder;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

@SpringBootApplication
@EnableScheduling
@ComponentScan
public class IshanKafkaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(IshanKafkaDemoApplication.class, args);
	}

	// private final KafkaTemplate<String, String> kafkaTemplate;
    // private final String topic = "your-topic-name"; // Replace with your Kafka topic

    // // Constructor Injection
    // public IshanKafkaDemoApplication(KafkaTemplate<String, String> kafkaTemplate) {
    //     this.kafkaTemplate = kafkaTemplate;
    // }

	//Bean is an object that Spring Manages for me 
	//When needed, I get the bean from the application context 
	@Bean
    public CommandLineRunner run() {
        return args -> System.out.println("Spring Boot application has started!");
    }

	// //Stream Cloud Stream itself will invoke this method on regular intervals 
	// @Bean
	// public Supplier<Message> producer() {
	// 	return () -> new Message(" jack from Streams");
	// }

	@Bean
	public Supplier<Message> producer() {
    AtomicInteger counter = new AtomicInteger(0);

		return () -> {
			int count = counter.getAndIncrement();
			Message message;

			if (count % 5 == 4) { // Every 5th message will be TypeBMessage
				message = new Message("Message of TypeA", "PayloadBad");
										
			} else { // Other messages will be TypeAMessage
				message = new Message("Message of TypeB", "PayloadGood");

			}
			return message;
		};
	}

	//Invoked when a message is sent to its binding 
	@Bean
	public Consumer<Message> consumer() {
		return message -> {
			if ("PayloadGood".equals(message.getPayload())) { // Example condition
				System.out.println("Filtered and received: " + message);
			} else {
				System.out.println("Message filtered out: " + message);
			}
		};
	}


}
