package com.algon.gkeeper.config;

import com.algon.gkeeper.service.ProcessingService;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaStreamsConfig {

  @Bean
  public Properties processingServiceProperties(
      @Value("${kafka.streams.bootstrap-servers}") String bootstrapServers,
      @Value("${kafka.streams.application-id}") String applicationId) {
    var properties = new Properties();
    properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    properties.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
    properties.put(
        StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    properties.put(
        StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

    return properties;
  }

  @Bean
  public ProcessingService processingService(
      @Qualifier("processingServiceProperties") Properties processingServiceProperties,
      @Value("${kafka.streams.input-topic}") String inputTopic,
      @Value("${kafka.streams.output-topic}") String outputTopic) {
    return new ProcessingService(processingServiceProperties, inputTopic, outputTopic);
  }
}
