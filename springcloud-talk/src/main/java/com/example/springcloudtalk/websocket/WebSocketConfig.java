package com.example.springcloudtalk.websocket;

/**
 * WebSocketConfig<br>
 * <p>
 * 作成日：2026/9/23<br>
 * 作成者：秦振兴<br>
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
