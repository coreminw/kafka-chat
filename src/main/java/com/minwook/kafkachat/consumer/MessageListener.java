package com.minwook.kafkachat.consumer;

import com.minwook.kafkachat.Model.Message;
import com.minwook.kafkachat.constants.KafkaConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Configuration
public class MessageListener {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @KafkaListener(topics = KafkaConstants.KAFKA_TOPIC, groupId = KafkaConstants.GROUP_ID) //@KafkaListener 어노테이션을 통해 Kafka로부터 메시지를 받을 수 있다.
    public void listen(Message message){
        log.info("sending via kafka listener!");
        simpMessagingTemplate.convertAndSend("/topic/group", message); //simpMessagingTemplate.convertAndSend를 통해 WebSocket으로 메시지를 전송한다.
    }
}
