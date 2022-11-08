package com.minwook.kafkachat.configuration;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.minwook.kafkachat.Model.Message;
import com.minwook.kafkachat.constants.KafkaConstants;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class ProducerConfiguration { //producer는 TOPIC에 메시지를 작성한다.

    @Bean
    public ProducerFactory<String, Message> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfigurations());
    }

    @Bean
    public Map<String, Object> producerConfigurations() {
        Map<String, Object> configurations = new HashMap<>();
        configurations.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKER); //BOOTSTRAP_SERVERS_CONFIG는 Kafka가 실행되는 주소를 설정을 한다.

        //KEY_SERIALIZER_CLASS_CONFIG와 VALUE_SERIALIZER_CLASS_CONFIG는 Kafka로 보내는 데이터의 키와 값을 직렬화한다.
        //문자열을 넘길땐 StringSerializer.class를, JSON 데이터를 넘길 땐 JsonSerializer.class를 적어주면 된다.
        configurations.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configurations.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configurations;
    }

    @Bean
    public KafkaTemplate<String, Message> kafkaTemplate(){ //KafkaTemplate을 통해 TOPIC에 메시지를 보낼 수 있다.
        return new KafkaTemplate<>(producerFactory());
    }
}
