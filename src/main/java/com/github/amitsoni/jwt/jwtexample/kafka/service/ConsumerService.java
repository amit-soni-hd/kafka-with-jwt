package com.github.amitsoni.jwt.jwtexample.kafka.service;

import com.github.amitsoni.jwt.jwtexample.dto.EmployeeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerService {

    private final String topic = "${kafka.topic.name}";

    @KafkaListener(topics = topic)
    public void consume(EmployeeDto employeeDto) {
        log.info("Consumed message : {}", employeeDto);
    }

}
