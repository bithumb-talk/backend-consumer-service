package com.bithumb.candlestick.service;

import com.bithumb.candlestick.domain.CandleStick;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.BDDMockito.given;
@ExtendWith(MockitoExtension.class)
class CandleServiceImplTest {
    @InjectMocks
    CandleServiceImpl candleService;

    @Mock
    @Resource(name = "redisTemplate")
    public ZSetOperations<String, CandleStick> zSetOperations;



    @BeforeEach
    void setUp() {
    }

    @Test
    void getCandleStickFromBithumb() throws IOException {
        given(UriComponentsBuilder.fromHttpUrl("https://api.bithumb.com/public/candlestick/BTC_KRW/24h").build()).willReturn(UriComponentsBuilder.fromHttpUrl("https://api.bithumb.com/public/candlestick/BTC_KRW/24h").build());

        List<CandleStick> responseBithumb = candleService.getCandleStickFromBithumb("BTC","24h");
        System.out.println(responseBithumb.iterator().next().getOpenPrice());

    }

    @Test
    void getCandleStick() throws IOException {
        Set<ZSetOperations.TypedTuple<CandleStick>> rankSet = new HashSet<ZSetOperations.TypedTuple<CandleStick>>();
        String[] candle = new String[6];
        candle[0] = "1";
        candle[1] = "2";
        candle[2] = "3";
        candle[3] = "4";
        candle[4] = "5";
        candle[5] = "6";

        rankSet.add(ZSetOperations.TypedTuple.of(CandleStick.of(candle),1.1));
        System.out.println(rankSet.iterator().next().getValue());
        given(zSetOperations.rangeWithScores("BTC_24h",0,-1)).willReturn(rankSet);
        List<CandleStick> result = candleService.getCandleStick("BTC","24h");

        assertThat(result.iterator().next().getClosePrice(),is("3"));
//        System.out.println(result.iterator().next().getOpenPrice());
    }
}