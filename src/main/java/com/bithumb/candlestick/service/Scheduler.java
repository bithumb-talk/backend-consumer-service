package com.bithumb.candlestick.service;

import com.bithumb.candlestick.controller.dto.CandleResponse;
import com.bithumb.coin.domain.Coin;
import com.bithumb.coin.service.CoinServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@RequiredArgsConstructor
public class Scheduler {
    private final CandleServiceImpl candleService;
    private final CoinServiceImpl coinService;
    private final RedisTemplate redisTemplate;

    @Scheduled(fixedDelay = 100000)
    public void scheduleFixedRateTask() throws IOException {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        HashMap<String, Coin> coins = coinService.getCoins();
        Iterator keyIterator = coins.keySet().iterator();

        while(keyIterator.hasNext()) {
            String symbol = keyIterator.next().toString();
            List<CandleResponse> candles = candleService.getCandleStick(symbol,"24H");
            for (CandleResponse candle: candles) {
                zSetOperations.add(symbol + "_24H", candle.getBaseTime(), Double.parseDouble(candle.getBaseTime()));
                System.out.println(candle);
            }
        }
//        Set<ZSetOperations.TypedTuple<String>> rankSet= zSetOperations.reverseRangeWithScores("BTC_24H",0,1);
//        System.out.println(rankSet.iterator().next().getScore());
//        System.out.println();
        System.out.println(
                "Fixed rate task - " + System.currentTimeMillis() / 1000);
        System.out.println("Current Thread : {}"+ Thread.currentThread().getName());
    }

}
