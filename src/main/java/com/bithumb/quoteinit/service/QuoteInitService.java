package com.bithumb.quoteinit.service;

import com.bithumb.quoteinit.controller.dto.QuoteInitResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Component
public interface QuoteInitService {
    public List<QuoteInitResponse> getQuoteInit() throws IOException, ParseException;
}
