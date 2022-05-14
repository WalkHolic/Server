package com.promenade.promenadeapp.dto;

import com.promenade.promenadeapp.domain.User.UserRoad;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRoadRequestDto {

    private String trailName;
    private String description;
    private double distance;
    private String startAddr;
    private String trailPoint;

    @Builder
    public UserRoadRequestDto(Long id, String trailName, String description, double distance, String startAddr, String trailPoint) {
        this.trailName = trailName;
        this.description = description;
        this.distance = distance;
        this.startAddr = startAddr;
        this.trailPoint = trailPoint;
    }

}
