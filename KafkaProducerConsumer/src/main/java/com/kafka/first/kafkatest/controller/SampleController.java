package com.kafka.first.kafkatest.controller;

import com.kafka.first.kafkatest.producer.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class SampleController {

    @Value("${message.topic.name}")
    private String topicName;

    @Value("${message.key}")
    private String messageKey;

    final String success = "published success";

    private KafkaProducer kafkaProducer;

    @Autowired
    public SampleController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/send/{message}")
    public String sendMessageToKafkaTopic(@PathVariable String message) {
        kafkaProducer.sendMessage(topicName, messageKey, message);

        log.info("Send Message in " + topicName + " : [" + message +"]");
        log.info(success);
        return "Send Message in " + topicName + " : [" + message +"]";
    }
}
