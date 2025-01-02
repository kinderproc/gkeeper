package com.algon.gkeeper.config;

import com.algon.gkeeper.data.Message;
import com.algon.gkeeper.service.KafkaProducerService;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;

@Configuration
public class KafkaProducerConfig {

  @Value("${kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Bean
  public ProducerFactory<String, Message> producerFactory() {
    var props = new HashMap<String, Object>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

    return new DefaultKafkaProducerFactory<>(props);
  }

  @Bean
  public KafkaTemplate<String, Message> kafkaTemplate(
      @Qualifier("producerFactory") ProducerFactory<String, Message> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }

  @Bean
  public KafkaProducerService kafkaProducerService(
      @Qualifier("kafkaTemplate") KafkaTemplate<String, Message> kafkaTemplate) {
    return new KafkaProducerService(kafkaTemplate);
  }
}
