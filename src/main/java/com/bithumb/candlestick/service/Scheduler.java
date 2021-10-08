package com.bithumb.candlestick.service;

import com.bithumb.candlestick.domain.CandleStick;
import com.bithumb.coin.domain.Coin;
import com.bithumb.coin.service.CoinServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class Scheduler {
    private final CandleServiceImpl candleService;
    private final CoinServiceImpl coinService;
    private final RedisTemplate redisTemplate;

    //24시간 배치스케줄러
    @Scheduled(fixedDelay = 1000*60*60*24)
    public void schedule24HTask() throws IOException {
        Double lastValue;
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        HashMap<String, Coin> coins = coinService.getCoins();
        Iterator keyIterator = coins.keySet().iterator();
        // redis에 데이터가 없을 경우
        // 모든 코인을 한번에 저장하기 때문에 BTC로 체크함.
        if (zSetOperations.size("BTC_24h") == 0) {
            lastValue = 0.0;
        } else{
            Set<ZSetOperations.TypedTuple<CandleStick>> rankSet= zSetOperations.reverseRangeWithScores("BTC_24h",0,0);
            lastValue= rankSet.iterator().next().getScore();
        }
        while(keyIterator.hasNext()) {
            String symbol = keyIterator.next().toString();
            List<CandleStick> candles = candleService.getCandleStickFromBithumb(symbol,"24h");
            Collections.reverse(candles);
            for (CandleStick candle: candles) {
                if ( Double.parseDouble(candle.getBaseTime()) <= lastValue){
                    break;
                }
//                System.out.println("[24시간 배치 스케줄러] "+symbol+", "+candle+" 추가 됨");

                zSetOperations.add(symbol + "_24h", candle,Double.parseDouble(candle.getBaseTime()));
            }
        }
        System.out.println(
                "Fixed rate task - " + System.currentTimeMillis() / 1000);
        System.out.println("Current Thread : {}"+ Thread.currentThread().getName());
    }

    //1시간 배치스케줄러
    @Scheduled(fixedDelay = 1000*60*60)
    public void schedule1HTask() throws IOException {
        Double lastValue;
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        HashMap<String, Coin> coins = coinService.getCoins();
        Iterator keyIterator = coins.keySet().iterator();
        // redis에 데이터가 없을 경우
        // 모든 코인을 한번에 저장하기 때문에 BTC로 체크함.
        if (zSetOperations.size("BTC_1h") == 0) {
            lastValue = 0.0;
        } else{
            Set<ZSetOperations.TypedTuple<CandleStick>> rankSet= zSetOperations.reverseRangeWithScores("BTC_1h",0,0);
            lastValue= rankSet.iterator().next().getScore();
        }
        while(keyIterator.hasNext()) {
            String symbol = keyIterator.next().toString();
            List<CandleStick> candles = candleService.getCandleStickFromBithumb(symbol,"1h");
            Collections.reverse(candles);
            for (CandleStick candle: candles) {
                if ( Double.parseDouble(candle.getBaseTime()) <= lastValue){
                    break;
                }
//                System.out.println("[1시간 배치 스케줄러] "+symbol+", "+candle+" 추가 됨");

                zSetOperations.add(symbol + "_1h", candle,Double.parseDouble(candle.getBaseTime()));
            }
        }
        System.out.println(
                "Fixed rate task - " + System.currentTimeMillis() / 1000);
        System.out.println("Current Thread : {}"+ Thread.currentThread().getName());
    }

    //30분 배치 스케줄러
    @Scheduled(fixedDelay = 1000*60*30)
    public void schedule30MTask() throws IOException {
        Double lastValue;
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        HashMap<String, Coin> coins = coinService.getCoins();
        Iterator keyIterator = coins.keySet().iterator();
        // redis에 데이터가 없을 경우
        // 모든 코인을 한번에 저장하기 때문에 BTC로 체크함.
        if (zSetOperations.size("BTC_30m") == 0) {
            lastValue = 0.0;
        } else{
            Set<ZSetOperations.TypedTuple<CandleStick>> rankSet= zSetOperations.reverseRangeWithScores("BTC_30m",0,0);
            lastValue= rankSet.iterator().next().getScore();
        }
        while(keyIterator.hasNext()) {
            String symbol = keyIterator.next().toString();
            List<CandleStick> candles = candleService.getCandleStickFromBithumb(symbol,"30m");
            Collections.reverse(candles);
            for (CandleStick candle: candles) {
                if ( Double.parseDouble(candle.getBaseTime()) <= lastValue){
                    break;
                }
//                System.out.println("[30분 배치 스케줄러] "+symbol+", "+candle+" 추가 됨");

                zSetOperations.add(symbol + "_30m", candle,Double.parseDouble(candle.getBaseTime()));
            }
        }
        System.out.println(
                "Fixed rate task - " + System.currentTimeMillis() / 1000);
        System.out.println("Current Thread : {}"+ Thread.currentThread().getName());
    }


    //10분 배치 스케줄러
    @Scheduled(fixedDelay = 1000*60*10)
    public void schedule10MTask() throws IOException {
        Double lastValue;
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        HashMap<String, Coin> coins = coinService.getCoins();
        Iterator keyIterator = coins.keySet().iterator();
        // redis에 데이터가 없을 경우
        // 모든 코인을 한번에 저장하기 때문에 BTC로 체크함.
        if (zSetOperations.size("BTC_10m") == 0) {
            lastValue = 0.0;
        } else{
            Set<ZSetOperations.TypedTuple<CandleStick>> rankSet= zSetOperations.reverseRangeWithScores("BTC_10m",0,0);
            lastValue= rankSet.iterator().next().getScore();
        }
        while(keyIterator.hasNext()) {
            String symbol = keyIterator.next().toString();
            List<CandleStick> candles = candleService.getCandleStickFromBithumb(symbol,"10m");
            Collections.reverse(candles);
            for (CandleStick candle: candles) {
                if ( Double.parseDouble(candle.getBaseTime()) <= lastValue){
                    break;
                }
//                System.out.println("[10분 배치 스케줄러] "+symbol+", "+candle+" 추가 됨");

                zSetOperations.add(symbol + "_10m", candle,Double.parseDouble(candle.getBaseTime()));
            }
        }
        System.out.println(
                "Fixed rate task - " + System.currentTimeMillis() / 1000);
        System.out.println("Current Thread : {}"+ Thread.currentThread().getName());
    }
}
