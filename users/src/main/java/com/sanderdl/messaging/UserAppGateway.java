package com.sanderdl.messaging;

import com.sanderdl.domain.User;
import com.sanderdl.messaging.dto.UserEvent;
import com.sanderdl.util.MessagingConverter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class UserAppGateway implements IGatewayObserver {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAppGateway.class);

    @Value("${kafka.topic}")
    private String topic;

    private MessageConsumer consumer = new MessageConsumer(this);

    @Autowired
    private MessageProducer producer;


    public void notifyServices(User user, MessageEvent messageEvent){
        UserEvent event = new UserEvent(
                user.getId(),
                messageEvent
        );

        String message = MessagingConverter.classToString(event);

        producer.send(topic, message);

    }

    @Override
    public void update(Object... param) {
        ConsumerRecord<String, String> record = (ConsumerRecord<String, String>) param[0];
        UserEvent event = MessagingConverter.stringToClass(record.value(), UserEvent.class);
        // TO-DO: Actually use the event for something.
        LOGGER.info("message received!");
    }
}
