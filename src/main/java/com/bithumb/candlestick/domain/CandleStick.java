package com.bithumb.candlestick.domain;

import com.bithumb.changerate.controller.dto.SortChangedRateResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.redis.core.ZSetOperations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@ToString @Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CandleStick implements Serializable {
    private String baseTime;
    private String openPrice;
    private String closePrice;
    private String highPrice;
    private String lowPrice;
    private String tradeVolume;



    public static CandleStick of(String[] candle) {
        return new CandleStick(candle[0],candle[1],candle[2],candle[3],candle[4],candle[5]);
    }

    public static List<CandleStick> ofArray(String[][] candles) {
        List<CandleStick> candlesResponse = new ArrayList<>();
        for (String[] candle: candles) {
            candlesResponse.add(CandleStick.of(candle));
        }
        return candlesResponse;
    }

    public static List<CandleStick> ofIterater(Set<ZSetOperations.TypedTuple<CandleStick>> rankSet) {
        Iterator<ZSetOperations.TypedTuple<CandleStick>> iterator = rankSet.iterator();
        List<CandleStick> rateDtos = new ArrayList<>();
        int i;

        for (i=0;i< rankSet.size();i++) {
            CandleStick values = iterator.next().getValue();
            rateDtos.add(values);
        }
        return rateDtos;
    }
}
