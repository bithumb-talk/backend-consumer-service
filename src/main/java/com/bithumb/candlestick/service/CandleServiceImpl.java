package com.bithumb.candlestick.service;

import com.bithumb.candlestick.controller.dto.CandleBithumbResponse;
import com.bithumb.candlestick.controller.dto.CandleResponse;
import com.bithumb.coin.domain.Coin;
import com.bithumb.coin.service.CoinServiceImpl;
import com.bithumb.common.response.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CandleServiceImpl implements CandleService {
    private final CoinServiceImpl coinService;

    @Value("${property.candlestickuri}")
    private String uri;

    @Override
    public List<CandleResponse> getCandleStick(String symbol, String chart_intervals) throws IOException {
        HashMap coins = coinService.getCoins();
        existsCoin(coins, symbol);
        String url = uri+symbol+"_KRW/"+chart_intervals;
        UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();
        String jsonInString = "";
        ObjectMapper mapper = new ObjectMapper();
        HttpHeaders header = new HttpHeaders();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        RestTemplate restTemplate = new RestTemplate(factory);
        HttpEntity<?> entity = new HttpEntity<>(header);

        ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET,entity, Map.class);
        jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultMap.getBody());

        String[][] candles = mapper.readValue(jsonInString, CandleBithumbResponse.class).getData();
        return CandleResponse.ofArray(candles);
    }
    private void existsCoin(HashMap coins, String symbol) throws IOException {
        Boolean exsitsCoin = coins.containsKey(symbol);
        if (!exsitsCoin) {
            throw new SecurityException(ErrorCode.COIN_NOT_EXISTS.getMessage());
        }
    }

}
