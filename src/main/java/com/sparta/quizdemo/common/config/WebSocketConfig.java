package com.sparta.quizdemo.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /*
    * 메시지 브로커 옵션
    * /app으로 시작하는 목적지를 가진 메시지가 애플리케이션의 @MessageMapping 어노테이션이 붙은 메서드로 라우팅
    */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    /*
    * STOMP 프로토콜을 사용하는 웹소켓 엔드포인트 등록
    * SockJS : 웹소켓을 지원하지 않는 브라우저에서도 통신
    */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp")
                .setAllowedOriginPatterns("*").withSockJS(); // ex) ws://localhost:8080/stomp
    }
}