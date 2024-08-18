package com.ishan.phadte;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ishan.phadte.dto.Message;


@SpringBootApplication
public class IshanKafkaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(IshanKafkaDemoApplication.class, args);
	}

	@Bean
    public CommandLineRunner run() {
        return args -> System.out.println("Spring Boot application has started!");
    }

	@Bean
	public Supplier<Message> producer() {
		return () -> new Message(" jack from Streams");
	}

	@Bean
	public Consumer<Message> consumer() {
		return message -> System.out.println("received " + message);
	}


}
