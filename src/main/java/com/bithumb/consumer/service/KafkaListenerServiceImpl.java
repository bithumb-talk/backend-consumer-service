package com.bithumb.consumer.service;

import com.bithumb.websocket.config.MessageSender;
import com.bithumb.websocket.domain.Quote;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class KafkaListenerServiceImpl implements KafkaListenerService {
    private final MessageSender messageSender;
    private final RedisTemplate redisTemplate;
    @KafkaListener(topics = "kafka-spring-producer-coin-test5",containerFactory = "coinKafkaListenerFactory")
    public void listenHeaders(
            @Payload Quote quote,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) throws IOException {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add("changerate",quote.getKorean(),Double.parseDouble(quote.getChgRate()));
        System.out.println("Received Message: \n"+ quote + "\n from partition: "+ partition);
        messageSender.sendMessage(quote);
    }
}
