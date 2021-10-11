package com.bithumb.changerate.service;

import com.bithumb.changerate.controller.dto.SortChangedRateResponse;
import com.netflix.discovery.converters.Auto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.ZSetOperations;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class RateServiceImplTest {
    @InjectMocks
    RateServiceImpl rateService;

    @Mock
    @Resource(name = "redisTemplate")
    public ZSetOperations<String, String> zSetOperations;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getSortChangeRate() {
        List<SortChangedRateResponse> sortChangedRateResponses = Arrays.asList(new SortChangedRateResponse(1,"비트코인",1.1));

        Set<ZSetOperations.TypedTuple<String>> rankSet = new HashSet<ZSetOperations.TypedTuple<String>>();
        rankSet.add(ZSetOperations.TypedTuple.of("비트코인",1.1));

        //given
        given(zSetOperations.reverseRangeWithScores("changerate",0,-1)).willReturn(rankSet);

        //when
        List<SortChangedRateResponse> result = rateService.getSortChangeRate();
        //then
        assertThat(result.iterator().next().getValue(), is(sortChangedRateResponses.iterator().next().getValue()));

    }
}