package com.algon.gkeeper.config;

import com.algon.gkeeper.data.Message;
import com.algon.gkeeper.service.KafkaConsumerService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

  @Value("${kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Value("${kafka.consumer.group-id}")
  private String groupId;

  @Bean
  public Map<String, Object> consumerConfig() {
    var consumerConfig = new HashMap<String, Object>();
    consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

    return consumerConfig;
  }

  @Bean
  public ConsumerFactory<String, Message> consumerFactory(
      @Qualifier("consumerConfig") Map<String, Object> consumerConfig) {
    return new DefaultKafkaConsumerFactory<>(
        consumerConfig, new StringDeserializer(), new JsonDeserializer<>(Message.class));
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, Message> kafkaListenerContainerFactory(
      @Qualifier("consumerFactory") ConsumerFactory<String, Message> consumerFactory) {
    var factory = new ConcurrentKafkaListenerContainerFactory<String, Message>();
    factory.setConsumerFactory(consumerFactory);

    return factory;
  }

  @Bean
  public KafkaConsumerService kafkaConsumerService() {
    return new KafkaConsumerService();
  }
}
