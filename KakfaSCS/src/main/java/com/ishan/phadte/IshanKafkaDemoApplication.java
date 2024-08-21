package com.ishan.phadte;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListOffsetsResult;
import org.apache.kafka.clients.admin.OffsetSpec;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.ishan.phadte.dto.Reservation;
import com.ishan.phadte.dto.Headers;

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
		return args -> {
            System.out.println("Spring Boot application has started!");
            // Print the number of messages in the queue
            // checkTopicSize(); // Replace with your topic name
        };
    }

	// @Bean
	// public Supplier<Message> producer() {
    // AtomicInteger counter = new AtomicInteger(0);

	// 	return () -> {
	// 		int count = counter.getAndIncrement();
	// 		Message message;
	// 		Headers headers = new Headers();

	// 		if (count % 5 == 4) { // Every 5th message will be TypeBMessage
	// 			headers.put("Type", "TypeA");
    //             headers.put("Priority", "High");
	// 			message = new Message(headers, "PayloadBad");
										
	// 		} else { // Other messages will be TypeAMessage
	// 			headers.put("Type", "TypeA");
    //             headers.put("Priority", "High");
	// 			message = new Message(headers, "PayloadGood");

	// 		}
	// 		return message;
	// 	};
	// }

	// //Invoked when a message is sent to its binding 
	// @Bean
	// public Consumer<Message> consumer() {
	// 	return message -> {
	// 		if ("PayloadGood".equals(message.getPayload())) { // Example condition
	// 			System.out.println("Filtered and received: " + message);
	// 		} else {
	// 			System.out.println("Message filtered out: " + message);
	// 		}
	// 	};
	// }
	// @Bean
	// public Supplier<String> producer() {
	// 	return () -> "Hello, Kafka!";
	// }

	// @Bean
    // public Consumer<String> consumer() {
    //     return message -> System.out.println("received " + message);
    // }

	@Autowired
    private StreamBridge streamBridge;

	@Value("${kafka.topic.name:reservations-topic}")
	private String topicName;



	// @Bean
    // public Supplier<Reservation> reservationProducer(){
    //     //streamBridge.send("reservationProducer-out-0", new Reservation("5"));
	// 	return () -> new Reservation("a","b");
    // }

    @Scheduled(cron = "*/2 * * * * *")
    public void sendMessage(){
		// Create a map of headers (you might want to customize this)
        Map<String, String> headers = new HashMap<>();
        headers.put("header1", "value1"); // Add your headers here
        headers.put("header2", "value2");
		System.out.println("Sent Message");

        streamBridge.send("reservationProducer-out-0", new Reservation("c","d", headers));
    }

	// @Scheduled(cron = "*/5 * * * * *") // Check every 5 seconds
    // public void checkTopicSize() {
    //     Properties props = new Properties();
    //     props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

    //     try (AdminClient adminClient = AdminClient.create(props)) {
    //         TopicPartition partition = new TopicPartition(topicName, 0); // Check the first partition

	// 		// Fetch latest offset
	// 		ListOffsetsResult listOffsetsResultLatest = adminClient.listOffsets(Collections.singletonMap(partition, OffsetSpec.latest()));
	// 		long latestOffset = listOffsetsResultLatest.partitionResult(partition).get().offset();
	// 		System.out.println("Latest offset for partition 0 of topic " + topicName + ": " + latestOffset);

	// 		// Fetch earliest offset
	// 		ListOffsetsResult listOffsetsResultEarliest = adminClient.listOffsets(Collections.singletonMap(partition, OffsetSpec.earliest()));
	// 		long earliestOffset = listOffsetsResultEarliest.partitionResult(partition).get().offset();
	// 		System.out.println("Earliest offset for partition 0 of topic " + topicName + ": " + earliestOffset);

	// 		// Calculate number of messages
	// 		long numberOfMessages = latestOffset - earliestOffset;
    //     	System.out.println("Number of messages in the queue for topic " + topicName + ": " + numberOfMessages);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

	@Bean
    public Consumer<Reservation> reservationConsumer() {
        return message -> System.out.println("received " + message);
    }



}
