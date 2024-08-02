package group6.ecommerce.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/user"); // Định nghĩa các chủ đề mà client có thể đăng ký nhận thông báo
        config.setApplicationDestinationPrefixes("/app"); // Định nghĩa tiền tố cho các message mapping trong controller
        config.setUserDestinationPrefix("/user"); // Định nghĩa tiền tố cho các chủ đề dành riêng cho từng user
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-notification").setAllowedOriginPatterns("*").withSockJS(); // Đăng ký endpoint WebSocket
    }

}
