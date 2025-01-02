package com.algon.gkeeper.handler;

import com.algon.gkeeper.data.Message;
import com.algon.gkeeper.service.KafkaProducerService;
import com.algon.gkeeper.service.ProcessingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
public class WebsocketHandler extends TextWebSocketHandler {

  private final KafkaProducerService kafkaProducerService;

  private final ProcessingService processingService;

  @Value("${kafka.producer.topic}")
  private String inputTopic;

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    var mapper = new ObjectMapper();
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    mapper.registerModule(new JavaTimeModule());
    var request = mapper.readValue(message.getPayload(), Message.class);

    switch (request.messageCode()) {
      case START_STREAM:
        processingService.startProcessing();
        break;
      case STOP_STREAM:
        processingService.stopProcessing();
        break;
      default:
        kafkaProducerService.send(inputTopic, request);
    }

    var response = String.format("Request %s successfully sent", request.id());
    session.sendMessage(new TextMessage(response));
  }
}
