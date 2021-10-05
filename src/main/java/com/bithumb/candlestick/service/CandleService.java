package com.bithumb.candlestick.service;

import com.bithumb.candlestick.domain.CandleStick;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public interface CandleService {
    public List<CandleStick> getCandleStickFromBithumb(String symbol, String chart_intervals) throws IOException;
    public List<CandleStick> getCandleStick(String symbol, String chart_intervals) throws IOException;
}
