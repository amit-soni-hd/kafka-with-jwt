package com.github.amitsoni.jwt.jwtexample.kafka.service;

import com.github.amitsoni.jwt.jwtexample.dto.EmployeeDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class ProducerService {

    @Autowired
    private KafkaTemplate<String, EmployeeDto> kafkaTemplate;

    @Async
    public void produceRecord(String topic, EmployeeDto employeeDto) {

        ProducerRecord<String, EmployeeDto> producerRecord = new ProducerRecord<>(topic, employeeDto);
        ListenableFuture<SendResult<String, EmployeeDto>> futureResponse = kafkaTemplate.send(producerRecord);
        futureResponse.addCallback(new ListenableFutureCallback<SendResult<String, EmployeeDto>>() {

            @Override
            public void onFailure(Throwable throwable) {
                log.error("Unable to send message : {}", throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, EmployeeDto> stringEmployeeDtoSendResult) {
                log.info("Message sent successfully with offset {}", stringEmployeeDtoSendResult.getRecordMetadata().offset());
                log.info("Message : {}", stringEmployeeDtoSendResult.getProducerRecord().value());
            }

        });

    }
}
