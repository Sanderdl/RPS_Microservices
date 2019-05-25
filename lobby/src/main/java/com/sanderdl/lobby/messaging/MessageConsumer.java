package com.sanderdl.lobby.messaging;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageConsumer extends Thread {
    private final KafkaConsumer<String, String> consumer;
    private final List<String> topics;
    private IGatewayObserver observer;

    private static final Logger logger = Logger.getLogger(MessageConsumer.class.getName());

    public MessageConsumer(String topic, String groupId, String clientId, IGatewayObserver observer){

        Properties configProperties = new Properties();
        configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        configProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId);

        consumer = new KafkaConsumer<>(configProperties);

        topics = new ArrayList<>();
        topics.add(topic);

        this.observer = observer;

        start();

    }

    @Override
    public void run() {
        try {
            consumer.subscribe(topics);

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
                for (ConsumerRecord<String, String> record : records) {
                    observer.update(record);
                }
            }
        } catch (WakeupException e) {
            logger.log(Level.INFO, e.getMessage(), e);

        } finally {
            consumer.close();
        }
    }
}
