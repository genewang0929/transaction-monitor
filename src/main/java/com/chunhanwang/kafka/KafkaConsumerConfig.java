//package com.chunhanwang.kafka;
//
//import org.apache.kafka.clients.consumer.*;
//import org.apache.kafka.clients.producer.*;
//import org.apache.kafka.common.serialization.*;
//import org.springframework.beans.factory.annotation.*;
//import org.springframework.context.annotation.*;
//import org.springframework.kafka.config.*;
//import org.springframework.kafka.core.*;
//import org.springframework.kafka.listener.*;
//
//import java.util.*;
//
//@Configuration
//public class KafkaConsumerConfig {
//    @Value("${spring.kafka.bootstrap-servers}")
//    private String bootstrapServers;
//
//    public Map<String, Object> consumerConfig() {
//        HashMap<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        return props;
//    }
//
//    @Bean
//    public ConsumerFactory<String, String> consumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(consumerConfig());
//    }
//
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> factory(ConsumerFactory<String, String> consumerFactory) {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory);
//        return factory;
//    }
//}
