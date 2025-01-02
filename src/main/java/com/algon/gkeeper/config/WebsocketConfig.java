package com.algon.gkeeper.config;

import com.algon.gkeeper.handler.WebsocketHandler;
import com.algon.gkeeper.service.KafkaProducerService;
import com.algon.gkeeper.service.ProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebsocketConfig implements WebSocketConfigurer {

  private final KafkaProducerService kafkaProducerService;

  private final ProcessingService processingService;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(webSocketHandler(), "/gkeeper");
  }

  @Bean
  public WebSocketHandler webSocketHandler() {
    return new WebsocketHandler(kafkaProducerService, processingService);
  }
}
