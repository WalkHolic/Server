package com.promenade.promenadeapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserRoadRequestDto {

    private String trailName;
    private String description;
    private double distance;
    private String startAddr;
    private List<List<Double>> trailPoints;
    private List<String> hashtag;

}
