package com.bithumb.quoteinit.service;

import com.bithumb.coin.domain.Coin;
import com.bithumb.coin.service.CoinServiceImpl;
import com.bithumb.quoteinit.controller.dto.QuoteInitBithumbResponse;
import com.bithumb.quoteinit.controller.dto.QuoteInitResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@RequiredArgsConstructor
@Service
public class QuoteInitServiceImpl implements QuoteInitService {
    private final CoinServiceImpl coinService;
    @Value("${property.desturi}")
    private String destUri;

    @Override
    public List<QuoteInitResponse> getQuoteInit() throws IOException, ParseException {
        String jsonInString = "";
        JSONParser jsonParser = new JSONParser();
        ObjectMapper mapper = new ObjectMapper();
        HttpHeaders header = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(header);
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(factory);

        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);

        UriComponents uri = UriComponentsBuilder.fromHttpUrl(destUri).build();
        ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET,entity, Map.class);
        jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultMap.getBody());

        QuoteInitBithumbResponse quoteResponse = mapper.readValue(jsonInString, QuoteInitBithumbResponse.class);

        String obj = mapper.writeValueAsString(quoteResponse.getData());
        JSONObject resultObj = (JSONObject)jsonParser.parse(obj);

        Iterator keyIterator = resultObj.keySet().iterator();
        Iterator valuesIterator = resultObj.values().iterator();
        HashMap<String, Coin> coins= coinService.getCoins();

        return QuoteInitResponse.ofIterater(keyIterator,valuesIterator,coins);
    }
}
