package com.bithumb.changerate.service;

import com.bithumb.changerate.controller.dto.SortChangedRateResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RateService {
    public List<SortChangedRateResponse> getSortChangeRate();
}
