package com.bithumb.consumer.config;

import com.bithumb.websocket.domain.Quote;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${property.kafka.server}")
    private String KAFKA_SERVER;
    @Value("${property.kafka.group}")
    private String GROUP;

    @Bean
    public ConsumerFactory<String, Quote> coinConsumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(Quote.class,false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Quote> coinKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Quote> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(coinConsumerFactory());
        return factory;
    }
}
