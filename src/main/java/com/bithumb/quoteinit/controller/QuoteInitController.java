package com.bithumb.quoteinit.controller;

import com.bithumb.quoteinit.controller.dto.QuoteInitResponse;
import com.bithumb.quoteinit.service.QuoteInitServiceImpl;
import com.bithumb.common.response.ApiResponse;
import com.bithumb.common.response.StatusCode;
import com.bithumb.common.response.SuccessCode;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Api
@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping("/quote_init")
@RequiredArgsConstructor
public class QuoteInitController {
    private final QuoteInitServiceImpl quoteInitService;

    @GetMapping
    public ResponseEntity<?> getQuoteInit() throws IOException, ParseException {
        List<QuoteInitResponse> quoteInitResponses = quoteInitService.getQuoteInit();
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
                SuccessCode.QUOTE_INIT_FINDALL_SUCCESS.getMessage(), quoteInitResponses);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
