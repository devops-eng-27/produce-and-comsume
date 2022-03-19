package com.kafka.first.kafkatest.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "${message.topic.name}", groupId = "${kafka.group-id}")
    public void listen(String message) {
        log.info("Received Message in group : [" + message +"]");
    }
}
