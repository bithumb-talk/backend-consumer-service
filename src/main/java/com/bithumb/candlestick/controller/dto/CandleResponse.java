package com.bithumb.candlestick.controller.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@ToString
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CandleResponse {
    private String baseTime;
    private String openPrice;
    private String closePrice;
    private String highPrice;
    private String lowPrice;
    private String tradeVolume;

    public static CandleResponse of(String[] candle) {
        return new CandleResponse(candle[0],candle[1],candle[2],candle[3],candle[4],candle[5]);
    }

    public static List<CandleResponse> ofArray(String[][] candles) {
        List<CandleResponse> candlesResponse = new ArrayList<>();
        for (String[] candle: candles) {
            candlesResponse.add(CandleResponse.of(candle));
        }
        return candlesResponse;
    }
}
