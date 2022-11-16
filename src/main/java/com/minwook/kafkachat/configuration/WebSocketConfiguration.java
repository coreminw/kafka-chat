package com.minwook.kafkachat.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker
@Configuration
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry){
        //endpoint에 작성한 값으로 클라이언트 측에서 연결을 한다.
        // setAllowedOrigins는 허용할 주소를 작성하면 된다. *은 모두 허용한다는 뜻이다.
        stompEndpointRegistry.addEndpoint("/my-chat").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry messageBrokerRegistry){
        // setApplicationDestinationPrefixes 는 메시지를 전송할 때 사용하는 url이다.
        messageBrokerRegistry.setApplicationDestinationPrefixes("/kafka");
        messageBrokerRegistry.enableSimpleBroker("/topic/");
    }
}
