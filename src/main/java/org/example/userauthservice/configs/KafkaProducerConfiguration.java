package org.example.userauthservice.configs;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
//import com.fasterxml.jackson.databind.ser.std.StringSerializer;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.example.userauthservice.dtos.EmailDto;
import org.example.userauthservice.models.User;
import org.example.userauthservice.producers.IProducer;
import org.example.userauthservice.producers.KafkaProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfiguration {

    private Map<String, Object> baseProducerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    private <T> ProducerFactory<String, T> producerFactory() {
        return new DefaultKafkaProducerFactory<>(baseProducerConfigs());
    }

    private <T> KafkaTemplate<String, T> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public IProducer<User> userProducer() {
        return new KafkaProducer<>(kafkaTemplate());
    }

    @Bean
    public IProducer<EmailDto> emailDtoIProducer() {
        return new KafkaProducer<>(kafkaTemplate());
    }

    @Bean
    public ProducerFactory<String, User> userProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, User> userKafkaTemplate() {
        return new KafkaTemplate<>(userProducerFactory());
    }


}


//
//    @Bean
//    public IProducer<Order> orderProducer() {
//        return new KafkaProducer<>(kafkaTemplate());
//    }

