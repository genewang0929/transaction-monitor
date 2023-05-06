package com.chunhanwang.kafka;

import org.springframework.kafka.annotation.*;
import org.springframework.stereotype.*;

@Component
public class KafkaListeners {
    @KafkaListener(topics = "ebankTopic", groupId = "groupId")
    void listener(String data) {
        System.out.println("Received " + data);
    }
}
