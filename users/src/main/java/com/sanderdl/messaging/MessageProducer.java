package com.sanderdl.messaging;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    void send(String topic, String payload) {
        kafkaTemplate.send(topic, payload);
    }

}
