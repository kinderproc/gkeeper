package com.algon.gkeeper.service;

import com.algon.gkeeper.data.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
public class KafkaConsumerService {

  @KafkaListener(
      topics = "${kafka.topics.metrics}",
      containerFactory = "kafkaListenerContainerFactory")
  public void listen(Message message) {
    log.info("Received client message: " + message);
  }
}
