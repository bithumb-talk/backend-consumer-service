package com.bithumb.changerate.controller.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@ToString @Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SortChangedRateResponse {
    private int rank;
    private String value;
    private Double score;


    public static List<SortChangedRateResponse> ofIterater(Set<ZSetOperations.TypedTuple<String>> rankSet) {
        Iterator<ZSetOperations.TypedTuple<String>> iterator = rankSet.iterator();
        List<SortChangedRateResponse> rateDtos = new ArrayList<>();
        int i;

        for (i=0;i< rankSet.size();i++) {
            ZSetOperations.TypedTuple<String> values = iterator.next();
            rateDtos.add(new SortChangedRateResponse(i+1,values.getValue(), values.getScore()));
        }
        return rateDtos;
    }
}
