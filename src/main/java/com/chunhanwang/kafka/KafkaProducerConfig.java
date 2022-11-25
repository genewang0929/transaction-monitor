//package com.chunhanwang.kafka;
//
//import org.apache.kafka.clients.producer.*;
//import org.apache.kafka.common.serialization.*;
//import org.springframework.beans.factory.annotation.*;
//import org.springframework.context.annotation.*;
//import org.springframework.kafka.core.*;
//
//import java.util.*;
//
//@Configuration
//public class KafkaProducerConfig {
//    @Value("${spring.kafka.bootstrap-servers}")
//    private String bootstrapServers;
//
//    public Map<String, Object> producerConfig() {
//        HashMap<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        return props;
//    }
//
//    @Bean
//    public ProducerFactory<String, String> producerFactory() {
//        return new DefaultKafkaProducerFactory<>(producerConfig());
//    }
//
//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {
//        return new KafkaTemplate<>(producerFactory);
//    }
//}
