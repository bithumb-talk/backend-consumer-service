package com.bithumb.candlestick.service;

import com.bithumb.candlestick.controller.dto.CandleResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public interface CandleService {
    public List<CandleResponse> getCandleStick(String symbol, String chart_intervals) throws IOException;
}
