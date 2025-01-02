package com.algon.gkeeper.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Properties;

@RequiredArgsConstructor
public class ProcessingService {

  private final Properties processingServiceProperties;

  private final String inputTopic;

  private final String outputTopic;

  private KafkaStreams streams;

  @PostConstruct
  private void postConstruct() {
    var builder = new StreamsBuilder();
    KStream<String, String> messages = builder.stream(inputTopic);
    messages.to(outputTopic, Produced.with(Serdes.String(), Serdes.String()));
    var topology = builder.build();
    streams = new KafkaStreams(topology, processingServiceProperties);
  }

  public void startProcessing() {
    streams.start();
  }

  public void stopProcessing() {
    streams.close();
  }
}
