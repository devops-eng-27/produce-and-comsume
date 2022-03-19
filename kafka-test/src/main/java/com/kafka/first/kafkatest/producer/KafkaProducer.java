package com.kafka.first.kafkatest.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class KafkaProducer {

    @Value("${message.topic.name}")
    private String topicName;

    @Value("${message.key}")
    private String messageKey;

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducer(
            KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topicName, String messageKey, String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, messageKey, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.info("Unable to send message=[" + message + "] due to :" + ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() +"]");
            }
        });
    }
}
