package com.bithumb.candlestick.service;

import com.bithumb.candlestick.controller.dto.CandleBithumbResponse;
import com.bithumb.candlestick.domain.CandleStick;
import com.bithumb.changerate.controller.dto.SortChangedRateResponse;
import com.bithumb.common.response.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Service
public class CandleServiceImpl implements CandleService {
    @Value("${property.candlestickuri}")
    private String uri;
    @Resource(name = "redisTemplate")
    public ZSetOperations<String, CandleStick> zSetOperations;
    @Override
    public List<CandleStick> getCandleStickFromBithumb(String symbol, String chart_intervals) throws IOException {
        String url = uri+symbol+"_KRW/"+chart_intervals;
        UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();
        System.out.println(uri);
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
        return CandleStick.ofArray(candles);
    }

    @Override
    public List<CandleStick> getCandleStick(String symbol, String chart_intervals) throws IOException {
//        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<CandleStick>> rankSet= zSetOperations.rangeWithScores(symbol+"_"+chart_intervals,0,-1);
        return CandleStick.ofIterater(rankSet);
    }
}
