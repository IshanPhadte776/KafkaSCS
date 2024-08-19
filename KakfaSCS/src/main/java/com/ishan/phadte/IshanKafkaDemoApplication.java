package com.ishan.phadte;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.ishan.phadte.dto.Message;


@SpringBootApplication
@EnableScheduling
public class IshanKafkaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(IshanKafkaDemoApplication.class, args);
	}

	//Bean is an object that Spring Manages for me 
	//When needed, I get the bean from the application context 
	@Bean
    public CommandLineRunner run() {
        return args -> System.out.println("Spring Boot application has started!");
    }

	//Stream Cloud Stream itself will invoke this method on regular intervals 
	// @Bean
	// public Supplier<Message> producer() {
	// 	return () -> new Message(" jack from Streams");
	// }

	//Invoked when a message is sent to its binding 
	@Bean
	public Consumer<Message> consumer() {
		return message -> System.out.println("received " + message);
	}

}
