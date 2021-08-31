package com.chatty.websocket;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/app"); // works with @SubscribeMapping
        config.setApplicationDestinationPrefixes("/chat"); // works with @MessageMapping
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
            .addEndpoint("/dashboard")
            .setAllowedOrigins("http://localhost:8080").withSockJS()
            .setDisconnectDelay(300 * 1000);
    }

}
