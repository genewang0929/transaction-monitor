package com.chunhanwang;
import com.chunhanwang.entity.*;
import com.chunhanwang.repository.*;
import com.chunhanwang.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class SynpulseBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SynpulseBackendApplication.class, args);
    }

}
