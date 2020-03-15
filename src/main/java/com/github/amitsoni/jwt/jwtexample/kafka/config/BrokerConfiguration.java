package com.github.amitsoni.jwt.jwtexample.kafka.config;

import com.github.amitsoni.jwt.jwtexample.dto.EmployeeDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;

@Configuration
@Slf4j
public class BrokerConfiguration {

    @Autowired
    private ProducerFactory producerFactory;

    @Bean
    public KafkaTemplate<String, EmployeeDto> operationKafkaTemplate() {
        KafkaTemplate<String, EmployeeDto> kafkaTemplate = new KafkaTemplate<String, EmployeeDto>(producerFactory, true);
        kafkaTemplate.setProducerListener(new ProducerListener<String, EmployeeDto>() {

            @Override
            public void onSuccess(ProducerRecord<String, EmployeeDto> record, RecordMetadata recordMetadata) {
                System.out.println("### Callback :: " + recordMetadata.topic() + " ; partition = "
                        + recordMetadata.partition()  +" with offset= " + recordMetadata.offset()
                        + " ; Timestamp : " + recordMetadata.timestamp() + " ; Message Size = " + recordMetadata.serializedValueSize());
            }

            @Override
            public void onError(ProducerRecord<String, EmployeeDto> producerRecord, Exception exception) {
                System.out.println("### Topic = " + producerRecord.topic() + " ; Message = " + producerRecord.value());
                exception.printStackTrace();
            }
        });
        return kafkaTemplate;
    }
}
