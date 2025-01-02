package com.algon.gkeeper.config;

import com.algon.gkeeper.handler.WebsocketHandler;
import com.algon.gkeeper.service.KafkaProducerService;
import com.algon.gkeeper.service.ProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

  @Value("${kafka.topics.metrics}")
  private String metricsTopic;

  @Value("${kafka.topics.device-states}")
  private String deviceStatesTopic;

  @Value("${kafka.topics.device-states}")
  private String gameStatesTopic;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(webSocketMetricHandler(metricsTopic), "/ws/device/metric");
    registry.addHandler(webSocketDeviceStateHandler(deviceStatesTopic), "/ws/device/state");
    registry.addHandler(webSocketGameStateHandler(gameStatesTopic), "/ws/game/state");
  }

  // TODO: change this three beans to one with Prototype scope
  @Bean
  public WebSocketHandler webSocketMetricHandler(String inputTopic) {
    return new WebsocketHandler(kafkaProducerService, processingService, inputTopic);
  }

  @Bean
  public WebSocketHandler webSocketDeviceStateHandler(String inputTopic) {
    return new WebsocketHandler(kafkaProducerService, processingService, inputTopic);
  }

  @Bean
  public WebSocketHandler webSocketGameStateHandler(String inputTopic) {
    return new WebsocketHandler(kafkaProducerService, processingService, inputTopic);
  }
}
