//package com.bithumb.consumer.service;
//
//import com.bithumb.websocket.config.MessageSender;
//import com.bithumb.websocket.domain.Quote;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class KafkaListenerServiceImpl implements KafkaListenerService {
//    private final MessageSender messageSender;
//    @KafkaListener(topics = "kafka-spring-producer-coin-test5",containerFactory = "coinKafkaListenerFactory")
//    public void listenHeaders(
//            @Payload Quote quote,
//            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) throws JsonProcessingException {
//        System.out.println("listen msg");
//        System.out.println("Received Message: \n"+ quote + "\n from partition: "+ partition);
//        messageSender.sendMessage(quote);
//    }
//}
