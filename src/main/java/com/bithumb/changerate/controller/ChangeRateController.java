package com.bithumb.changerate.controller;

import com.bithumb.changerate.controller.dto.SortChangedRateResponse;
import com.bithumb.changerate.service.RateServiceImpl;
import com.bithumb.common.response.ApiResponse;
import com.bithumb.common.response.StatusCode;
import com.bithumb.common.response.SuccessCode;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping("/changerate")
@RequiredArgsConstructor
public class ChangeRateController {
    private final RateServiceImpl riseService;

    @GetMapping
    public ResponseEntity<?> getRiseCoin() {
        List<SortChangedRateResponse> sortChangedRateResponse = riseService.getSortChangeRate();
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
                SuccessCode.RISE_COIN_FINDALL_SUCCESS.getMessage(),sortChangedRateResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
