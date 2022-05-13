package com.promenade.promenadeapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TestRequestDto {

    private Long userId;
    private String googleId;
    private String trailName;
    private String description;
    private double distance;
    private String startAddr;
    private String trailPoint;

    @Builder
    public TestRequestDto(Long userId, String googleId, String trailName, String description, double distance, String startAddr, String trailPoint) {
        this.userId = userId;
        this.googleId = googleId;
        this.trailName = trailName;
        this.description = description;
        this.distance = distance;
        this.startAddr = startAddr;
        this.trailPoint = trailPoint;
    }

}
