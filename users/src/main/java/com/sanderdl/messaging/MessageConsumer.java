package com.sanderdl.messaging;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private final IGatewayObserver observer;

    MessageConsumer(IGatewayObserver observer){
        this.observer = observer;
    }

    @KafkaListener(topics = "${kafka.topic}")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        observer.update(consumerRecord);
    }
}
