//package com.bithumb.websocket.config;
//
//import com.bithumb.websocket.domain.Quote;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.web.util.HtmlUtils;
//
//@Service
//public class MessageSender {
//    @Autowired
//    private SimpMessagingTemplate brokerMessagingTemplate;
//
//    public void sendMessage(Quote quote) throws JsonProcessingException {
//        System.out.println("sendMessage");
//        ObjectMapper mapper = new ObjectMapper();
//        String json=mapper.writeValueAsString(quote);
//        System.out.println(json);
//        System.out.println(HtmlUtils.htmlEscape(quote.getClosePrice()));
//        this.brokerMessagingTemplate.convertAndSend("/topic/coin", quote);
//    }
//
//}
//
