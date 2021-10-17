package com.bithumb.candlestick.controller;

import com.bithumb.candlestick.domain.CandleStick;
import com.bithumb.candlestick.service.CandleServiceImpl;
import com.bithumb.common.response.ApiResponse;
import com.bithumb.common.response.StatusCode;
import com.bithumb.common.response.SuccessCode;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

//@CrossOrigin(origins = "*", allowCredentials = "false")
@Api
@RestController
@RequestMapping("/candlestick")
@RequiredArgsConstructor
@Slf4j
public class CandleController {
    private final CandleServiceImpl candleService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CandleController.class);

    @GetMapping("{symbol}/{chart_intervals}")
    public ResponseEntity<?> getCandleStick(@PathVariable(value="symbol") String symbol, @PathVariable(value = "chart_intervals")String chart_intervals) throws IOException {
        LOGGER.info("차트 데이터");

        List<CandleStick> candleStick = candleService.getCandleStick(symbol, chart_intervals);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
                SuccessCode.CANDLESTICK_FINDALL_SUCCESS.getMessage(), candleStick);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
