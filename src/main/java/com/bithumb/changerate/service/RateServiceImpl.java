package com.bithumb.changerate.service;

import com.bithumb.changerate.controller.dto.SortChangedRateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RateServiceImpl implements RateService {
    @Resource(name = "redisTemplate")
    public ZSetOperations<String, String> zSetOperations;
    @Override
    public List<SortChangedRateResponse> getSortChangeRate() {
        Set<ZSetOperations.TypedTuple<String>> rankSet= zSetOperations.reverseRangeWithScores("changerate",0,-1);
        return SortChangedRateResponse.ofIterater(rankSet);
    }

}
