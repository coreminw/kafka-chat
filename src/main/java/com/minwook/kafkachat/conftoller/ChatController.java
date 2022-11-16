package com.minwook.kafkachat.conftoller;

import com.minwook.kafkachat.Model.Message;
import com.minwook.kafkachat.constants.KafkaConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/kafka")
public class ChatController {

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate; //kafkaTemplate.send 메소드를 통해 메시지가 전송된다.

    @PostMapping(value = "/publish")
    public void sendMessage(@RequestBody Message message){
        log.info("Produce message: ", message.toString());
        message.setTimestamp(LocalDateTime.now().toString());

        try {
            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/group")
    public Message broadcastGroupMessage(@Payload Message message) {
        return message;
    }

}
