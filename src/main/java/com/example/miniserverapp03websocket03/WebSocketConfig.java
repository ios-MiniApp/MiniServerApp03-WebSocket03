package com.example.miniserverapp03websocket03;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

// 参考
// https://stackoverflow.com/questions/52185059/use-java-websocket-api-in-spring-boot-application
// https://github.com/simasch/spring-boot-websocket/blob/master/src/main/java/com/example/websocket/configuration/WebSocketConfiguration.java
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //registry.addHandler(unitWebSocketHandler, "/unit").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketController webSocketController() {
        return new WebSocketController();
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
