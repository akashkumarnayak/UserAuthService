package org.example.userauthservice.producers;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class KafkaProducer<T> implements IProducer<T> {

    private final KafkaTemplate<String, T> kafkaTemplate;


    public KafkaProducer(KafkaTemplate<String, T> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(String topic, T message) {
        kafkaTemplate.send(topic, message);
    }
}
