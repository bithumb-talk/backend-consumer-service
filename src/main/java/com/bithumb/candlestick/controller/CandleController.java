package com.bithumb.candlestick.controller;

import com.bithumb.candlestick.domain.CandleStick;
import com.bithumb.candlestick.service.CandleServiceImpl;
import com.bithumb.common.response.ApiResponse;
import com.bithumb.common.response.StatusCode;
import com.bithumb.common.response.SuccessCode;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Api
@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping("/candlestick")
@RequiredArgsConstructor
public class CandleController {
    private final CandleServiceImpl candleService;

    @GetMapping("{symbol}/{chart_intervals}")
    public ResponseEntity<?> getCandleStick(@PathVariable(value="symbol") String symbol, @PathVariable(value = "chart_intervals")String chart_intervals) throws IOException {
        List<CandleStick> candleStick = candleService.getCandleStick(symbol, chart_intervals);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
                SuccessCode.CANDLESTICK_FINDALL_SUCCESS.getMessage(), candleStick);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
