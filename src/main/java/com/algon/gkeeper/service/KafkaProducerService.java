package com.algon.gkeeper.service;

import com.algon.gkeeper.data.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class KafkaProducerService {

  private final KafkaTemplate<String, Message> kafkaTemplate;

  @Value("${kafka.producer.topic}")
  private String topic;

  public void send(String topic, Message message) {
    kafkaTemplate.send(topic, message);
  }
}
