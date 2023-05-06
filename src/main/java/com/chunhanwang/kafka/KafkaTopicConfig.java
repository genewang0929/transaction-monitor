package com.chunhanwang.kafka;

import org.apache.kafka.clients.admin.*;
import org.springframework.context.annotation.*;
import org.springframework.kafka.config.*;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic ebankTopic() {
        return TopicBuilder.name("ebankTopic")
                .build();
    }
}
