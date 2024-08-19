package com.ishan.phadte;

import com.ishan.phadte.dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//This class is an bean
@Component
@EnableScheduling
public class KafkaProducer {

    //Tries to inject the required bean object into this StreamBridge Object
    @Autowired
    private StreamBridge streamBridge;

    // @Scheduled(cron = "*/2 * * * * *")
    // public void sendMessage(){
    //     streamBridge.send("producer-out-0", new Message("jack from StreamBridge"));
    // }

    private int messageCount = 0;
    private static final int MAX_MESSAGES = 10;

    @Scheduled(fixedRate = 500)
    public void sendMessage() {
        if (messageCount < MAX_MESSAGES) {
            Message message = new Message("Message number " + (messageCount + 1));
            messageCount++;
            streamBridge.send("producer-out-0", message);

        } else {
            System.out.println("Finished sending messages.");
            // Optionally, you can stop the application or disable the scheduled task
            // SpringApplication.exit(context, () -> 0); // Uncomment this if you want to exit the application
        }
    }
}